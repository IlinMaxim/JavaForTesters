package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void initContactCreation() {
    click((By.linkText("add new")));
  }

  public void initContactModification(){
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"),contactData.getFirstName());
    type(By.name("middlename"),contactData.getMiddleName());
    type(By.name("lastname"),contactData.getLastName());
    type(By.name("nickname"),contactData.getNickname());
    type(By.name("title"),contactData.getTitle());
    type(By.name("company"),contactData.getCompany());
    type(By.name("address"),contactData.getAddress());
    type(By.name("home"),contactData.getHomeTelephone());
    type(By.name("mobile"),contactData.getMobileTelephone());
    type(By.name("work"),contactData.getWorkTelephone());
    type(By.name("fax"),contactData.getFax());
    type(By.name("email"),contactData.getFirstEmail());
  }
  public void selectContact() {
    click(By.name("selected[]"));
  }
  public void deleteSelectedContacts(){
    click(By.xpath("//input[@value='Delete']"));
  }

  public void submitDeletionContacts() {
    wd.switchTo().alert().accept();
  }
  public void submitContactModification() {
    click(By.name("update"));
  }
}
