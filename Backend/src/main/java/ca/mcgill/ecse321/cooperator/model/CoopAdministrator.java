package ca.mcgill.ecse321.cooperator.model;
import javax.persistence.ManyToMany;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopAdministrator extends SystemUser{
private Set<Employer> employer;

@ManyToMany
public Set<Employer> getEmployer() {
   return this.employer;
}

public void setEmployer(Set<Employer> employers) {
   this.employer = employers;
}

private Set<Student> student;
   
   @ManyToMany
public Set<Student> getStudent() {
      return this.student;
   }
   
   public void setStudent(Set<Student> students) {
      this.student = students;
   }
   
   }
