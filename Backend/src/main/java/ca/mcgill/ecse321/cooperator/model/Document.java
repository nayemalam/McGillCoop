package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Document{
   private CoopTerm coopTerm;
   
   @ManyToOne(optional=false)
   public CoopTerm getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(CoopTerm coopTerm) {
      this.coopTerm = coopTerm;
   }
   
   private DocumentName docName;

public void setDocName(DocumentName value) {
    this.docName = value;
}
public DocumentName getDocName() {
    return this.docName;
}
private CoOperatorSystem coOperatorSystem;

@ManyToOne(optional=false)
public CoOperatorSystem getCoOperatorSystem() {
   return this.coOperatorSystem;
}

public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
   this.coOperatorSystem = coOperatorSystem;
}

private Date dueDate;

public void setDueDate(Date value) {
    this.dueDate = value;
}
public Date getDueDate() {
    return this.dueDate;
}
private Date submittedDate;

public void setSubmittedDate(Date value) {
    this.submittedDate = value;
}
public Date getSubmittedDate() {
    return this.submittedDate;
}
}
