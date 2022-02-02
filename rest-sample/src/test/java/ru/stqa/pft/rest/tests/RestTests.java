package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.*;

public class RestTests {

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
    String jsonString = getExucutor()
            .execute(Request.Get("http://demo.bugify.com/api/issues.json"))
            .returnContent()
            .asString();

    JsonElement parsed = new JsonParser().parse(jsonString);
    JsonElement jsonIssues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(jsonIssues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private Executor getExucutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "123");
  }

  private int createIssue(Issue newIssue) throws IOException {
    String jsonString = getExucutor()
            .execute(Request.Post("http://demo.bugify.com/api/issues.json")
                    .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                            new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent()
            .asString();

    JsonElement parsed = new JsonParser().parse(jsonString);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

}
