package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddGroup extends TestBase {
  GroupData groupToAdd = new GroupData();
  ContactData contact = new ContactData();

  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    Contacts contacts = app.db().contacts();
    ContactData contactForPrecondition = new ContactData()
            .withFirstName("FirstName")
            .withMiddleName("MiddleName")
            .withLastName("LastName")
            .withNickname("Nickname")
            .withMobilePhone("MobilePhone")
            .withFirstEmail("Email");

    if (groups.size() == 0) {
      app.goTo().groupPage();
      GroupData newGroup = new GroupData()
              .withName("GroupForTest")
              .withHeader("GroupHeader")
              .withFooter("GroupFooter");
      app.group().create(newGroup);
      app.goTo().homePage();
      groups = app.db().groups();
    }

    if (contacts.size() == 0) {
      app.contact().create(contactForPrecondition);
      contacts = app.db().contacts();
    }

    while (!verifyPossibilityAddGroupToContact(contact = contacts.iterator().next(), groups)) {
      contacts.remove(contact);
      if (contacts.size() == 0) {
        app.contact().create(contactForPrecondition);
        contacts.add(contactForPrecondition.withId(app.db().getLastContactId()));
      }
    }

    groupToAdd = getGroupForAdding(contact, groups);
  }

  @Test
  public void testContactAddGroup() {
    app.contact().addGroupToContact(contact, groupToAdd);

    ContactData contactAfter = app.db().getContactById(contact);
    assertThat(contactAfter, equalTo(contact.withGroup(groupToAdd)));
  }

  public GroupData getGroupForAdding(ContactData contact, Groups groups) {
    Groups contactGroups = contact.getGroups();
    GroupData missingGroup = null;
    for (GroupData group : groups) {
      if (!contactGroups.contains(group)) {
        missingGroup = group;
      }
    }
    return missingGroup;
  }

  public boolean verifyPossibilityAddGroupToContact(ContactData contact, Groups groups) {
    if (contact.getGroups().size() == groups.size()) {
      return false;
    }
    return true;
  }
}
