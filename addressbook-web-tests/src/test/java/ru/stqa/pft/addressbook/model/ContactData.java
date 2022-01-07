package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String nickname;
  private final String title;
  private final String company;
  private final String address;
  private final String homeTelephone;
  private final String mobileTelephone;
  private final String workTelephone;
  private final String fax;
  private final String firstEmail;
  private String group;

  public ContactData(String firstName, String middleName, String lastName, String nickname, String title, String company, String address, String homeTelephone, String mobileTelephone, String workTelephone, String fax, String firstEmail, String group) {
    this.id = Integer.MAX_VALUE;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    this.address = address;
    this.homeTelephone = homeTelephone;
    this.mobileTelephone = mobileTelephone;
    this.workTelephone = workTelephone;
    this.fax = fax;
    this.firstEmail = firstEmail;
    this.group = group;
  }

  public ContactData(int id,String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = null;
    this.lastName = lastName;
    this.nickname = null;
    this.title = null;
    this.company = null;
    this.address = null;
    this.homeTelephone = null;
    this.mobileTelephone = null;
    this.workTelephone = null;
    this.fax = null;
    this.firstEmail = null;
    this.group = null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getNickname() {
    return nickname;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getHomeTelephone() {
    return homeTelephone;
  }

  public String getMobileTelephone() {
    return mobileTelephone;
  }

  public String getWorkTelephone() {
    return workTelephone;
  }

  public String getFax() {
    return fax;
  }

  public String getFirstEmail() {
    return firstEmail;
  }

  public String getGroup() {
    return group;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", middleName='" + middleName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", nickname='" + nickname + '\'' +
            ", title='" + title + '\'' +
            ", company='" + company + '\'' +
            ", address='" + address + '\'' +
            ", homeTelephone='" + homeTelephone + '\'' +
            ", mobileTelephone='" + mobileTelephone + '\'' +
            ", workTelephone='" + workTelephone + '\'' +
            ", fax='" + fax + '\'' +
            ", firstEmail='" + firstEmail + '\'' +
            ", group='" + group + '\'' +
            '}';
  }

  public int getId() {
    return id;
  }
}
