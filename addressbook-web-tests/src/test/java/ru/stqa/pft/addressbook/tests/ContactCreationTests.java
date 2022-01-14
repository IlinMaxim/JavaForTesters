package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    File photo = new File("src/test/resources/randomMan.jpg");
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("PersonOne")
            .withMiddleName("MiddleName")
            .withLastName("LastName")
            .withNickname("Nickname")
            .withFirstEmail("FirstEmail")
            .withPhoto(photo)
            .withGroup("newGroup");

    app.contact().create(contact);
    Contacts after = app.contact().all();

    Assert.assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testContactCreationWithoutCreatedGroup() {
    Contacts before = app.contact().all();
    ContactData contactWithoutGroup = new ContactData().withFirstName("PersonTwo")
            .withMiddleName("MiddleName")
            .withLastName("LastName")
            .withNickname("Nickname")
            .withFirstEmail("FirstEmail")
            .withGroup("notCreatedGroup");

    app.contact().create(contactWithoutGroup);
    Contacts after = app.contact().all();

    Assert.assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(
            before.withAdded(contactWithoutGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));

  }
}

