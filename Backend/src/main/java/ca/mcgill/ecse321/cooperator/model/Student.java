package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Student extends User{
   private int studentId;

public void setStudentId(int value) {
    this.studentId = value;
}
@Id
public int getStudentId() {
    return this.studentId;
}
   private Set<CoopTerm> coopTerm;
   
   @OneToMany(mappedBy="student" )
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }
   
   private CooperatorSystem cooperatorSystem;
   
   @ManyToOne(optional=false)
   public CooperatorSystem getCooperatorSystem() {
      return this.cooperatorSystem;
   }
   
   public void setCooperatorSystem(CooperatorSystem cooperatorSystem) {
      this.cooperatorSystem = cooperatorSystem;
   }
   
   private CoopAdministrator coopAdministrator;
   
   @ManyToOne(optional=false)
   public CoopAdministrator getCoopAdministrator() {
      return this.coopAdministrator;
   }
   
   public void setCoopAdministrator(CoopAdministrator coopAdministrator) {
      this.coopAdministrator = coopAdministrator;
   }
   
   }
