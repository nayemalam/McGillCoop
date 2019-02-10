package ca.mcgill.ecse321.cooperator.model;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;


public class CooperatorSystem{
/**
    * <pre>
    *           1..1     0..*
    * CooperatorSystem ------------------------> CoopTerm
    *           cooperatorSystem        &gt;       coopTerm
    * </pre>
    */
   private Set<CoopTerm> coopTerm;
   
   public Set<CoopTerm> getCoopTerm() {
      if (this.coopTerm == null) {
         this.coopTerm = new HashSet<CoopTerm>();
      }
      return this.coopTerm;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * CooperatorSystem ------------------------> CoopAdministrator
    *           cooperatorSystem        &gt;       coopAdministrator
    * </pre>
    */
   private Set<CoopAdministrator> coopAdministrator;
   
   public Set<CoopAdministrator> getCoopAdministrator() {
      if (this.coopAdministrator == null) {
         this.coopAdministrator = new HashSet<CoopAdministrator>();
      }
      return this.coopAdministrator;
   }
   
   private int systemId;
   
   public void setSystemId(int value) {
      this.systemId = value;
   }
   
   public int getSystemId() {
      return this.systemId;
   }
   
      
   }
