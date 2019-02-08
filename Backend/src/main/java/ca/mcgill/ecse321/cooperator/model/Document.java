package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Document{
   private CoopTerm coopTerm;
   
   @ManyToOne(optional=false)
   public CoopTerm getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(CoopTerm coopTerm) {
      this.coopTerm = coopTerm;
   }
   
   }
