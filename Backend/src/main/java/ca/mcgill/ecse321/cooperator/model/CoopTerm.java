package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
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
   
   private Set<Document> document;
   
   @OneToMany(mappedBy="coopTerm" , cascade={CascadeType.ALL})
   public Set<Document> getDocument() {
      return this.document;
   }
   
   public void setDocument(Set<Document> documents) {
      this.document = documents;
   }
   
   private Employer employer;
   
   @ManyToOne(optional=false)
   public Employer getEmployer() {
      return this.employer;
   }
   
   public void setEmployer(Employer employer) {
      this.employer = employer;
   }
   
   }
