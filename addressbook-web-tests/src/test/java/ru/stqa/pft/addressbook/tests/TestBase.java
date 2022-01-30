package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws IOException {
    app.init();
  }

  @BeforeMethod(alwaysRun = true)
  public void logTestStart(Method m, Object[] o) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(o));
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("Stop test " + m.getName());
  }

  public void verifyGroupListInUi() {
    if (app.group().isVerifyUi()) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();


      Set<GroupData> dbGroupsWithoutParameters = dbGroups.stream()
              .map((g) -> new GroupData()
                      .withId(g.getId())
                      .withName(g.getName())).
                      collect(Collectors.toSet());

    //  assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet())));

      assertThat(uiGroups, equalTo(dbGroupsWithoutParameters));
    }
  }

}
