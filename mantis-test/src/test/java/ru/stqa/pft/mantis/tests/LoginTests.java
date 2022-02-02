package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.Issue;

import javax.xml.rpc.ServiceException;
import java.io.IOException;

public class LoginTests extends TestBase{

  @Test
  public void testLogin() throws IOException, ServiceException {
    Issue loginBug = new Issue().withId(1);
    skipIfNotFixed(loginBug.getId());

    HttpSession session = app.newSession();
    Assert.assertTrue(session.login("administrator", "root99"));
    Assert.assertTrue(session.isLoggedInAs("administrator"));
  }
}
