package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;


public class User{
private int coopAdminId;
   
   public void setCoopAdminId(int value) {
      this.coopAdminId = value;
   }
   
   public int getCoopAdminId() {
      return this.coopAdminId;
   }
   
   private int studentId;
   
   public void setStudentId(int value) {
      this.studentId = value;
   }
   
   public int getStudentId() {
      return this.studentId;
   }
   
   private int employerId;
   
   public void setEmployerId(int value) {
      this.employerId = value;
   }
   
   public int getEmployerId() {
      return this.employerId;
   }
   
   private String name;

public void setName(String value) {
    this.name = value;
}

public String getName() {
    return this.name;
}
}
