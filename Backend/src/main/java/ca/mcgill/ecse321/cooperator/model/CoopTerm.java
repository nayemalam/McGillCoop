package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.Set;
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
private Date endDate;

public void setEndDate(Date value) {
this.endDate = value;
}
public Date getEndDate() {
return this.endDate;
}
   private int termId;

public void setTermId(int value) {
    this.termId = value;
}
@Id
public int getTermId() {
    return this.termId;
}
private Employer employer;

@ManyToOne(optional=false)
public Employer getEmployer() {
   return this.employer;
}

public void setEmployer(Employer employer) {
   this.employer = employer;
}

   private Set<Document> document;
   
   @OneToMany(mappedBy="coopTerm" )
   public Set<Document> getDocument() {
      return this.document;
   }
   
   public void setDocument(Set<Document> documents) {
      this.document = documents;
   }
   
   }
