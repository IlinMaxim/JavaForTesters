package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (!app.contact().isThereAContact()) {
      app.contact().create(new ContactData()
              .withFirstName("FirstName")
              .withMiddleName("MiddleName")
              .withLastName("LastName")
              .withGroup("test1"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.stream().iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstName("FirstName1")
            .withLastName("LastName1");

    app.contact().initContactModification(modifiedContact);
    app.contact().modify(contact);
    app.goTo().homePage();
    Contacts after = app.contact().all();

    Assert.assertEquals(before.size(), after.size());
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}