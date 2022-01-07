package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("PersonOne", "MiddleName", "LastName", "Nickname", "Title", "Company", "Address", "HomeTelephone", "MobileTelephone", "WorkTelephone", "Fax", "FirstEmail", "test1");
    app.getContactHelper().createNewContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();

    Assert.assertEquals(after.size(), before.size() + 1);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

  @Test
  public void testContactCreationWithoutCreatedGroup() {
    app.getContactHelper().createNewContact(new ContactData("PersonTwo", "MiddleName", "LastName", "Nickname", "Title", "Company", "Address", "HomeTelephone", "MobileTelephone", "WorkTelephone", "Fax", "FirstEmail", "notCreatedGroup"));
  }

}

