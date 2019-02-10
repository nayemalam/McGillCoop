package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

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
   
   }
