package ca.mcgill.ecse321.cooperator.model;
import javax.persistence.ManyToMany;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopAdministrator extends SystemUser{
private Integer adminId;

public void setAdminId(Integer value) {
this.adminId = value;
}
public Integer getAdminId() {
return this.adminId;
}
private Set<Student> student;
   
   @ManyToMany(mappedBy="coopAdministrator")
public Set<Student> getStudent() {
      return this.student;
   }
   
   public void setStudent(Set<Student> students) {
      this.student = students;
   }
   
   }
