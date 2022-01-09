package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (!app.contact().isThereAContact()) {
      app.contact().create(new ContactData()
              .withFirstName("FirstName")
              .withMiddleName("MiddleName")
              .withLastName("LastName"));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.stream().iterator().next();

    app.contact().delete(deletedContact);
    app.goTo().homePage();
    Contacts after = app.contact().all();

    Assert.assertEquals(after.size(), before.size() - 1);
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(deletedContact)));
  }
}
