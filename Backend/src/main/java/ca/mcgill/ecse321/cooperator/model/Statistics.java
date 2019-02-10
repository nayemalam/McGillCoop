package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Statistics{
   private Student student;
   
   @OneToOne(optional=false)
   public Student getStudent() {
      return this.student;
   }
   
   public void setStudent(Student student) {
      this.student = student;
   }
   
   private int numberOfCoopTerm;

public void setNumberOfCoopTerm(int value) {
    this.numberOfCoopTerm = value;
}
public int getNumberOfCoopTerm() {
    return this.numberOfCoopTerm;
}
}
