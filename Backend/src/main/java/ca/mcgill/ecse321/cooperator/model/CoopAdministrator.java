package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;


@Entity
public class CoopAdministrator extends User{
private Set<Student> student;

@ManyToMany(mappedBy="coopAdministrator")
public Set<Student> getStudent() {
   return this.student;
}

public void setStudent(Set<Student> students) {
   this.student = students;
}

private String administratorID;

private void setAdministratorID(String value) {
   this.administratorID = value;
}

private String getAdministratorID() {
   return this.administratorID;
}

}
