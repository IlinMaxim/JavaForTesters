package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("FirstName1", "MiddleName1", "LastName1", "Nickname1", "Title1", "Company1", "Address1", "HomeTelephone1", "MobileTelephone1", "WorkTelephone1", "Fax1", "FirstEmail1"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
  }
}
