package ca.mcgill.ecse321.cooperator.model;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopAdministrator extends User{
private CooperatorSystem cooperatorSystem;
   
   @ManyToOne(optional=false)
   public CooperatorSystem getCooperatorSystem() {
      return this.cooperatorSystem;
   }
   
   public void setCooperatorSystem(CooperatorSystem cooperatorSystem) {
      this.cooperatorSystem = cooperatorSystem;
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
