package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;

import javax.xml.rpc.ServiceException;
import java.io.IOException;

public class LoginTests extends TestBase{

  @Test
  public void testLogin() throws IOException, ServiceException {
    skipIfNotFixed(1);

    HttpSession session = app.newSession();
    Assert.assertTrue(session.login("administrator", "root99"));
    Assert.assertTrue(session.isLoggedInAs("administrator"));
  }
}
