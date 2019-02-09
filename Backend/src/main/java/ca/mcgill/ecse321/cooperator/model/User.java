package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class User{
   
   private String name;

   public void setName(String value) {
       this.name = value;
   }
   
   public String getName() {
       return this.name;
   }
   
   private String username;

   public void setUsername(String value) {
       this.username = value;
   }
   
   public String getUsername() {
       return this.username;
   }
   
   private String password;

   public void setPassword(String value) {
       this.password = value;
   }
   
   public String getPassword() {
       return this.password;
   }
   
   private CoOperatorSystem coOperatorSystem;
   
   @ManyToOne(optional=false)
   public CoOperatorSystem getCoOperatorSystem() {
      return this.coOperatorSystem;
   }
   
   public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
      this.coOperatorSystem = coOperatorSystem;
   }
   
}
