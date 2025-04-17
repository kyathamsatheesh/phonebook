package com.cse5382.assignment.Model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;



@Entity
@Table(name="phonebook")
public class PhoneBookEntry   {

  @Column
  private String name = null;


  @Column
  @Id
  @Pattern(
//	        regexp = "^(\\+1\\s?)?(\\(\\d{3}\\)|\\d{3})[-.\\s]?\\d{3}[-.\\s]?\\d{4}$",
	        regexp = "^(\\+\\d{1,3}\\s?)?(011\\s?)?(\\d{1,5}|\\(\\d{2,5}\\))([-.\\s]?\\d{3,5}){1,3}$",
	        message = "Invalid phone number format. Use US standard format"
	    )
  private String phoneNumber = null;

  public PhoneBookEntry name(String name) {
    this.name = name;
    return this;
  }


    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PhoneBookEntry phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }


    public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PhoneBookEntry phoneBookEntry = (PhoneBookEntry) o;
    return Objects.equals(this.name, phoneBookEntry.name) &&
        Objects.equals(this.phoneNumber, phoneBookEntry.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, phoneNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PhoneBookEntry {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}
