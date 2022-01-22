package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {
    Contacts before = app.db().contacts();

    app.contact().create(contact);
    Contacts after = app.db().contacts();

    Assert.assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testContactCreationWithoutCreatedGroup() {
    Contacts before = app.db().contacts();
    ContactData contactWithoutGroup = new ContactData()
            .withFirstName("PersonTwo")
            .withMiddleName("MiddleName")
            .withLastName("LastName")
            .withNickname("Nickname")
            .withMobilePhone("MobilePhone")
            .withFirstEmail("FirstEmail")
            .withGroup("notCreatedGroup");

    app.contact().create(contactWithoutGroup);
    Contacts after = app.db().contacts();

    Assert.assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(
            before.withAdded(contactWithoutGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));

  }
}

