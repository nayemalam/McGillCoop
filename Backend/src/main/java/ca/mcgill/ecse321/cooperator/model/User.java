package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class User{
   private CoOperatorSystem cooperatorSystem1;
   
   @ManyToOne(optional=false)
   public CoOperatorSystem getCooperatorSystem1() {
      return this.cooperatorSystem1;
   }
   
   public void setCooperatorSystem1(CoOperatorSystem cooperatorSystem1) {
      this.cooperatorSystem1 = cooperatorSystem1;
   }
   
   }
