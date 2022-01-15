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
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getFirstEmail());
    attach(By.name("photo"), contactData.getPhoto());

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
    wd.findElement(By.id("search_count"));
    List<WebElement> elements = wd.findElements(By.cssSelector("tr"));
    elements.remove(0);
    int line = 0;
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.cssSelector("input")).getAttribute("id"));
      String lastName = element.findElements(By.cssSelector("td")).get(1).getText();
      String firstName = element.findElements(By.cssSelector("td")).get(2).getText();
      String address = element.findElements(By.cssSelector("td")).get(3).getText();
      String allMails = element.findElements(By.cssSelector("td")).get(4).getText();
      String allPhones = element.findElements(By.cssSelector("td")).get(5).getText();

      contactsCache.add(new ContactData().withId(id)
              .withFirstName(firstName)
              .withLastName(lastName)
              .withAddress(address)
              .withAllPhones(allPhones)
              .withAllMails(allMails)
              .withLine(line));
      line++;
    }
    return contactsCache;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact);
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    String secondPhone =  wd.findElement(By.name("phone2")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String firstEmail = wd.findElement(By.name("email")).getAttribute("value");
    String secondEmail = wd.findElement(By.name("email2")).getAttribute("value");
    String thirdEmail = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withFirstName(firstName)
            .withLastName(lastName)
            .withAddress(address)
            .withHomePhone(homePhone)
            .withMobilePhone(mobilePhone)
            .withWorkPhone(workPhone)
            .withSecondPhone(secondPhone)
            .withFirstEmail(firstEmail)
            .withSecondEmail(secondEmail)
            .withThirdEmail(thirdEmail);
  }
}

