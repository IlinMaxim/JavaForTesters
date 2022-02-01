package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteGroup extends TestBase {
  GroupData groupForDelete = new GroupData();
  ContactData contact = new ContactData();

  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    Contacts contacts = app.db().contacts();

    GroupData groupForPrecondition = new GroupData()
            .withName("GroupForTest")
            .withHeader("GroupHeader")
            .withFooter("GroupFooter");

    ContactData contactForPrecondition = new ContactData()
            .withFirstName("FirstName")
            .withMiddleName("MiddleName")
            .withLastName("LastName")
            .withNickname("Nickname")
            .withMobilePhone("MobilePhone")
            .withFirstEmail("Email");

    if (groups.size() == 0 && contacts.size() == 0) {
      app.goTo().groupPage();
      app.group().create(groupForPrecondition);
      app.goTo().homePage();
      app.contact().create(contactForPrecondition.withGroup(groupForPrecondition));
      app.goTo().homePage();

      groups = app.db().groups();
      contacts = app.db().contacts();
    }


    if (groups.size() == 0) {
      app.goTo().groupPage();
      app.group().create(groupForPrecondition);
      groups = app.db().groups();
      app.goTo().homePage();
      app.contact().addGroupToContact(contact = contacts.iterator().next(), groupForPrecondition);
      app.goTo().homePage();
      contacts = app.db().contacts();
    }

    if (contacts.size() == 0) {
      app.contact().create(contactForPrecondition.withGroup(groups.iterator().next()));
      contacts = app.db().contacts();
    }

    contact = createPossibilityDeleteGroupFromContact(contacts, groups);
    groups = app.db().groups();


    groupForDelete = getGroupForDeleting(contact, groups);
  }

  @Test
  public void testContactDeeteGroup() {
    System.out.println(1);
    app.contact().deleteGroupFromContact(contact, groupForDelete);
    ContactData contactAfter = app.db().getContactById(contact);

    System.out.println(contact);
    System.out.println(contact.withoutGroup(groupForDelete));

    assertThat(contactAfter, equalTo(contact.withoutGroup(groupForDelete)));
  }

  public ContactData createPossibilityDeleteGroupFromContact(Contacts contacts, Groups groups) {
    ContactData contactForTest = null;
    for (ContactData contact1 : contacts) {
      if (contact1.getGroups().size() >= 1) {
        contactForTest = contact1;
      } else {
        GroupData addedGroup = groups.iterator().next();
        app.contact().addGroupToContact(contact1, addedGroup);
        app.goTo().homePage();
        contactForTest = contact1.withGroup(addedGroup);
      }
    }
    return contactForTest;
  }

  public GroupData getGroupForDeleting(ContactData contact, Groups groups) {
    Groups contactGroups = contact.getGroups();
    GroupData deletingGroup = null;
    for (GroupData group : groups) {
      if (contactGroups.contains(group)) {
        deletingGroup = group;
      }
    }
    return deletingGroup;
  }
}