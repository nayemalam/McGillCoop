package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;

@Entity
public class Employer extends User{
   private int employerId;

public void setEmployerId(int value) {
    this.employerId = value;
}
public int getEmployerId() {
    return this.employerId;
}
private String companyName;

public void setCompanyName(String value) {
    this.companyName = value;
}
public String getCompanyName() {
    return this.companyName;
}
}
