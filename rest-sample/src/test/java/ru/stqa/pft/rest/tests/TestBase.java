package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import ru.stqa.pft.rest.model.Issue;

import java.util.Set;

public class TestBase {
  String baseUrl = "https://bugify.stqa.ru/api/";

  public Set<Issue> getIssues() {
    String jsonString = RestAssured.get(baseUrl + "issues.json").asString();
    JsonElement parsed = new JsonParser().parse(jsonString);
    JsonElement jsonIssues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(jsonIssues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  public int createIssue(Issue newIssue) {
    String jsonString = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post(baseUrl + "issues.json").asString();

    JsonElement parsed = new JsonParser().parse(jsonString);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public boolean isIssueOpen(int issueId) {
    String jsonString = RestAssured.get(String.format(baseUrl + "issues/%s.json", issueId)).asString();

    JsonElement parsed = new JsonParser().parse(jsonString);
    JsonElement jsonIssues = parsed.getAsJsonObject().get("issues");
    Set<Issue> issues = new Gson().fromJson(jsonIssues, new TypeToken<Set<Issue>>() {
    }.getType());

    Issue selectedIssue = issues.iterator().next();

    if(selectedIssue.getStateName().equals("Resolved") || selectedIssue.getStateName().equals("Closed")) {
      return false;
    } else {
      return true;
    }
  }

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}
