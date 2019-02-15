package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.ManyToMany;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Student extends SystemUser {
   private Set<CoopTerm> coopTerm;

   @OneToMany(mappedBy = "student")
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }

   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }

   private String program;

   public void setProgram(String value) {
      this.program = value;
   }

   public String getProgram() {
      return this.program;
   }

   private Integer studentId;

   public void setStudentId(Integer value) {
      this.studentId = value;
   }

   public Integer getStudentId() {
      return this.studentId;
   }

}
