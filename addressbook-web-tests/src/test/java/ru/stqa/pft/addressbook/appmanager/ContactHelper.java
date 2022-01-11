package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;


public class ContactHelper extends HelperBase {

  private Contacts contactsCache;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void initContactCreation() {
    click((By.linkText("add new")));
  }

  public void initContactModification(ContactData contact) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(contact.getLine()).click();
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("mobile"), contactData.getMobileTelephone());
    type(By.name("email"), contactData.getFirstEmail());

    if (creation) {
      if (isGroupNamePresent(contactData.getGroup())) {
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void submitDeletionContacts() {
    wd.switchTo().alert().accept();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create(ContactData contactData) {
    initContactCreation();
    fillContactForm(contactData, true);
    submitContactCreation();
    contactsCache = null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    fillContactForm(contact, false);
    submitContactModification();
    contactsCache = null;
  }

  public void delete(ContactData deletedContact) {
    selectContact(deletedContact.getLine());
    deleteSelectedContacts();
    submitDeletionContacts();
    contactsCache = null;
  }

  public boolean isGroupNamePresent(String groupName) {
    wd.findElement(By.name("new_group")).click();
    return isElementPresent(By.xpath("//*[contains(text(),'" + groupName + "')]"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public Contacts all() {
    if (contactsCache != null) {
      return new Contacts(contactsCache);
    }

    contactsCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr"));
    elements.remove(0);
    int line = 0;
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.cssSelector("input")).getAttribute("id"));
      String lastName = element.findElements(By.cssSelector("td")).get(1).getText();
      String firstName = element.findElements(By.cssSelector("td")).get(2).getText();

      contactsCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).withLine(line));
      line++;
    }
    return contactsCache;
  }
}

