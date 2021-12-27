package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if(!app.getContactHelper().isThereAContact()){
      app.getContactHelper().createNewContact(new ContactData("FirstName", "MiddleName", "LastName", null, null, null,null,null,null,null,null,null, "test1"), true);
    }
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("FirstName1", "MiddleName1", "LastName1", "Nickname1", "Title1", "Company1", "Address1", "HomeTelephone1", "MobileTelephone1", "WorkTelephone1", "Fax1", "FirstEmail1", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
  }
}