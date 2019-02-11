package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User{
private String firstName;

public void setFirstName(String value) {
this.firstName = value;
}
public String getFirstName() {
return this.firstName;
}
   private String emailAddress;

public void setEmailAddress(String value) {
    this.emailAddress = value;
}
public String getEmailAddress() {
    return this.emailAddress;
}
private String userName;

public void setUserName(String value) {
    this.userName = value;
}
public String getUserName() {
    return this.userName;
}
private String password;

public void setPassword(String value) {
    this.password = value;
}
public String getPassword() {
    return this.password;
}
private String lastName;

public void setLastName(String value) {
    this.lastName = value;
}
@Id
public String getLastName() {
    return this.lastName;
}
}
