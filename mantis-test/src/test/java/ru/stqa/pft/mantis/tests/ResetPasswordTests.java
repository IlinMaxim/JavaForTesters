package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.io.IOException;
import java.util.List;

public class ResetPasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

  @Test
  public void testResetPassword() throws IOException {
    String adminLogin = app.getProperty("web.adminLogin");
    String adminPassword = app.getProperty("web.adminPassword");
    String newUserPassword = "newPassword";

    Users users = app.db().users();
    users.remove(users.getUserByUserName(adminLogin));

    app.registration().login(adminLogin, adminPassword);
    app.goTo().managePage();
    app.goTo().manageUsers();
    UserData user = users.iterator().next();
    app.registration().resetUserPassword(user);
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 60000);

    String confirmationLink = app.mail().findConfirmationLink(mailMessages, user.getEmail());
    app.registration().finish(confirmationLink, newUserPassword);

    HttpSession session = app.newSession();
    Assert.assertTrue(session.login(user.getUsername(), newUserPassword));
    Assert.assertTrue(session.isLoggedInAs(user.getUsername()));
  }

}
