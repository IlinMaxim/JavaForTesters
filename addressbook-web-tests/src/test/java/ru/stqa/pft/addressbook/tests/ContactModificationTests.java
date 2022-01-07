package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if(!app.getContactHelper().isThereAContact()){
      app.getContactHelper().createNewContact(new ContactData("FirstName", "MiddleName", "LastName", null, null, null,null,null,null,null,null,null, "test1"));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().initContactModification(before.size()-1);
    ContactData contact = new ContactData(before.get(before.size()-1).getId(),"FirstName1",  "LastName1");
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitContactModification();
    app.goTo().homePage();

    List<ContactData> after = app.getContactHelper().getContactList();



    Assert.assertEquals(before.size(),after.size());

    before.remove(before.size()-1);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }
}