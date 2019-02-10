package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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
}
