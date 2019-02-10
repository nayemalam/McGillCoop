package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopAdministrator extends User{
   private int coopAdminId;

public void setCoopAdminId(int value) {
    this.coopAdminId = value;
}
@Id
public int getCoopAdminId() {
    return this.coopAdminId;
}
   private CooperatorSystem cooperatorSystem;
   
   @OneToOne(optional=false)
   public CooperatorSystem getCooperatorSystem() {
      return this.cooperatorSystem;
   }
   
   public void setCooperatorSystem(CooperatorSystem cooperatorSystem) {
      this.cooperatorSystem = cooperatorSystem;
   }
   
   private Set<Student> student;
   
   @OneToMany(mappedBy="coopAdministrator" )
   public Set<Student> getStudent() {
      return this.student;
   }
   
   public void setStudent(Set<Student> students) {
      this.student = students;
   }
   
   }
