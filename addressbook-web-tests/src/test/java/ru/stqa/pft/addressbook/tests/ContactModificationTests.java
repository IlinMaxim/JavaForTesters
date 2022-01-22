package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if(app.db().contacts().size() == 0)
      app.contact().create(new ContactData()
              .withFirstName("FirstName")
              .withMiddleName("MiddleName")
              .withLastName("LastName")
              .withGroup("test1"));
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.stream().iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstName("ModifiedName")
            .withMiddleName("ModifiedMiddleName")
            .withLastName("ModifiedLastName")
            .withNickname("ModifiedNickName")
            .withMobilePhone("ModifiedPhone")
            .withFirstEmail("Modified@EMAIL.RU");

    app.contact().initContactModificationById(modifiedContact);
    app.contact().modify(contact);
    app.goTo().homePage();

    Contacts after = app.db().contacts();

    Assert.assertEquals(before.size(), after.size());
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));


  }
}