package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.sql.Date;

@Entity
public class CoopTerm{
   private Student student;
   
   @ManyToOne(optional=false)
   public Student getStudent() {
      return this.student;
   }
   
   public void setStudent(Student student) {
      this.student = student;
   }
   
   private Set<Document> document;
   
   @OneToMany(mappedBy="coopTerm" , cascade={CascadeType.ALL})
   public Set<Document> getDocument() {
      return this.document;
   }
   
   public void setDocument(Set<Document> documents) {
      this.document = documents;
   }
   
   private Date startDate;

public void setStartDate(Date value) {
    this.startDate = value;
}
public Date getStartDate() {
    return this.startDate;
}
private Date dueDate;

public void setDueDate(Date value) {
    this.dueDate = value;
}
public Date getDueDate() {
    return this.dueDate;
}
   private CoOperatorSystem coOperatorSystem;
   
   @ManyToOne(optional=false)
   public CoOperatorSystem getCoOperatorSystem() {
      return this.coOperatorSystem;
   }
   
   public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
      this.coOperatorSystem = coOperatorSystem;
   }
   
   private CoopAdministrator coopAdministrator;
   
   @ManyToOne(optional=false)
   public CoopAdministrator getCoopAdministrator() {
      return this.coopAdministrator;
   }
   
   public void setCoopAdministrator(CoopAdministrator coopAdministrator) {
      this.coopAdministrator = coopAdministrator;
   }
   
   }
