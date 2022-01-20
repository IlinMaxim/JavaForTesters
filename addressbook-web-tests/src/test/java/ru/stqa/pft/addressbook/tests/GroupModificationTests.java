package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {
/*
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("newGroup"));
    }
  }
 */

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("newGroup"));
    }
  }

  @Test
  public void testGroupModification() {
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();

    app.goTo().groupPage();
    GroupData group = new GroupData().withId(modifiedGroup.getId())
            .withName("newMaximGroup")
            .withHeader("test1")
            .withFooter("test1");

    app.group().modify(group);

    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedGroup).withAdded(group)));
  }

/*
  @Test
  public void testGroupModification() {
    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();

    GroupData group = new GroupData().withId(modifiedGroup.getId())
            .withName("newMaximGroup")
            .withHeader("test1")
            .withFooter("test1");

    app.group().modify(group);

    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedGroup).withAdded(group)));
  }
 */
}
