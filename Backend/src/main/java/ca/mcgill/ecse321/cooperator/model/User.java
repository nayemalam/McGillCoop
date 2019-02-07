package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import ca.mcgill.ecse321.cooperator.model.CoOperatorSystem;
import javax.persistence.ManyToOne;

@Entity
public class User{
private CoOperatorSystem coOperatorSystem;

@ManyToOne(optional=false)
public CoOperatorSystem getCoOperatorSystem() {
   return this.coOperatorSystem;
}

public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
   this.coOperatorSystem = coOperatorSystem;
}

private String firstName;

private void setFirstName(String value) {
   this.firstName = value;
}

private String getFirstName() {
   return this.firstName;
}

private String lastName;

private void setLastName(String value) {
   this.lastName = value;
}

private String getLastName() {
   return this.lastName;
}

private String username;

private void setUsername(String value) {
   this.username = value;
}

private String getUsername() {
   return this.username;
}

private String password;

private void setPassword(String value) {
   this.password = value;
}

private String getPassword() {
   return this.password;
}

}
