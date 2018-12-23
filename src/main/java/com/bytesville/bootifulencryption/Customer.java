package com.bytesville.bootifulencryption;

import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

  @Id
  private String id;

  private String firstName;

  private String lastName;

  private String emailAddress;

  @Convert(converter = TransitConverter.class)
  private String creditCardNumber;

  public Customer() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public Customer(String id, String firstName, String lastName, String emailAddress,
      String creditCardNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.creditCardNumber = creditCardNumber;
  }

  @Override public String toString() {
    return "Customer{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailAddress='" + emailAddress + '\'' +
        ", creditCardNumber='" + creditCardNumber + '\'' +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Customer)) return false;
    Customer customer = (Customer) o;
    return Objects.equals(getId(), customer.getId()) &&
        Objects.equals(getFirstName(), customer.getFirstName()) &&
        Objects.equals(getLastName(), customer.getLastName()) &&
        Objects.equals(getEmailAddress(), customer.getEmailAddress()) &&
        Objects.equals(getCreditCardNumber(), customer.getCreditCardNumber());
  }

  @Override public int hashCode() {

    return Objects.hash(getId(), getFirstName(), getLastName(), getEmailAddress(),
        getCreditCardNumber());
  }
}
