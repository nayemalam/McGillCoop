package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;


public class User{
   private String name;

public void setName(String value) {
    this.name = value;
}

public String getName() {
    return this.name;
}
}
