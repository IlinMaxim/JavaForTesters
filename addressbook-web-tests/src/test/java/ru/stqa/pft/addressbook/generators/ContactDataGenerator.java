package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "contacts count")
  public int count;

  @Parameter(names = "-f", description = "target file")
  public String file;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);

    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    saveAsJson(contacts, new File(file));
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withFirstName(String.format("FirstNameContact %s", i))
              .withMiddleName(String.format("MiddleNameContact %s", i))
              .withLastName(String.format("LastNameContact %s", i))
              .withNickname(String.format("Nickname %s", i))
              .withMobilePhone(String.format("MobilePhone %s", i))
              .withFirstEmail(String.format("Email %s", i)));
    }
    System.out.println(contacts);
    return contacts;
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String contactAsString = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(contactAsString);
    }
  }

}
