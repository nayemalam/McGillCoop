package ca.mcgill.ecse321.cooperator.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

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
   
   @OneToMany(mappedBy="coopTerm" )
   public Set<Document> getDocument() {
      return this.document;
   }
   
   public void setDocument(Set<Document> documents) {
      this.document = documents;
   }
   
}
