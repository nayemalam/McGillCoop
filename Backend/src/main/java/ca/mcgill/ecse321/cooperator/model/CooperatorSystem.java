package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class CooperatorSystem{
   private int systemId;

public void setSystemId(int value) {
    this.systemId = value;
}
@Id
public int getSystemId() {
    return this.systemId;
}
   private Set<Student> student;
   
   @OneToMany(mappedBy="cooperatorSystem" , cascade={CascadeType.ALL})
   public Set<Student> getStudent() {
      return this.student;
   }
   
   public void setStudent(Set<Student> students) {
      this.student = students;
   }
   
   private CoopAdministrator coopAdministrator;
   
   @OneToOne(mappedBy="cooperatorSystem" , cascade={CascadeType.ALL}, optional=false)
   public CoopAdministrator getCoopAdministrator() {
      return this.coopAdministrator;
   }
   
   public void setCoopAdministrator(CoopAdministrator coopAdministrator) {
      this.coopAdministrator = coopAdministrator;
   }
   
   }
