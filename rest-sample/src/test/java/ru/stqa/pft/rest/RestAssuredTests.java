package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

  @BeforeClass
  public void init(){
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490","1");
  }

  @Test
  public void createIssueTest() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue();
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(oldIssues, newIssues);
    System.out.println(oldIssues);
  }

  private Set<Issue> getIssues() throws IOException {
    String jsonString = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();

    JsonElement parsed = new JsonParser().parse(jsonString);
    JsonElement jsonIssues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(jsonIssues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private int createIssue(Issue newIssue) throws IOException {
    String jsonString = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post("http://demo.bugify.com/api/issues.json").asString();

    JsonElement parsed = new JsonParser().parse(jsonString);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }
}
