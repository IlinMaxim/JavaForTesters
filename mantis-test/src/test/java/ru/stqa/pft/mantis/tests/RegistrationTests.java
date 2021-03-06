package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class RegistrationTests extends TestBase{

 // @BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

//  @AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }

  // Получение почты на встроенный в тесты почтовый сервер
  @Test(enabled = false)
  public void testRegistration() throws InterruptedException, IOException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s",now);
    String email = String.format("user%s@localhost.localdomen",now);
    String password = "password";
    app.registration().start(user,email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    String confirmationLink = app.mail().findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);

    assertTrue(app.newSession().login(user,password));
  }

  @Test
  public void testRegistrationWithExternalMailServer() throws InterruptedException, IOException, MessagingException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s",now);
    String email = String.format("user%s@localhost.localdomen",now);
    String password = "password";
    app.james().createUser(user,password);
    app.registration().start(user,email);
    List<MailMessage> mailMessages = app.james().waitForMail(user,password,60000);
    String confirmationLink = app.mail().findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);

    assertTrue(app.newSession().login(user,password));
  }

}
