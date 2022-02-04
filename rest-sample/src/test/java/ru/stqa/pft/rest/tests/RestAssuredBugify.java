package ru.stqa.pft.rest.tests;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredBugify extends TestBase {
  @BeforeClass
  public void init(){
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490","1");
  }

  @Test
  public void createIssueTest() {
    skipIfNotFixed(1);

    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withDescription("MaximIlinIssue").withSubject("JavaForTesters");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(oldIssues, newIssues);
  }

}
