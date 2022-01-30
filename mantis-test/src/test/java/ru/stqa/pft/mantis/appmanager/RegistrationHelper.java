package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

import static java.lang.Thread.sleep;


public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) throws InterruptedException {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[type='submit']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("button[type='submit']"));
  }

  public void login(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[type='submit']"));

    type(By.name("password"), password);
    click(By.cssSelector("input[type='submit']"));
  }

  public void resetUserPassword(UserData user) {
    goToUserManagePage(user.getId());
    click(By.cssSelector("[value = 'Reset Password']"));
  }

  private void goToUserManagePage(int id) {
    wd.findElement(By.cssSelector(String.format("[href='manage_user_edit_page.php?user_id=%s']", id))).click();
  }
}
