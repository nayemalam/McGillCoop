package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
public class CoopAdministrator extends User{
   private Set<Student> student;
   
   @OneToMany(mappedBy="coopAdministrator" )
   public Set<Student> getStudent() {
      return this.student;
   }
   
   public void setStudent(Set<Student> students) {
      this.student = students;
   }
   
   private int adminId;

public void setAdminId(int value) {
    this.adminId = value;
}
public int getAdminId() {
    return this.adminId;
}
   private Set<CoopTerm> coopTerm;
   
   @ManyToMany(mappedBy="coopAdministrator" )
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }
   
   }
