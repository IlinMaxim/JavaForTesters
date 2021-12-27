package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    if(!app.getContactHelper().isThereAContact()){
      app.getContactHelper().createNewContact(new ContactData("FirstName", "MiddleName", "LastName", null, null, null,null,null,null,null,null,null, "test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().submitDeletionContacts();
  }
}
