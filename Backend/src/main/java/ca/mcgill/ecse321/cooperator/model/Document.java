package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
   
   private boolean isSubmitted;

public void setIsSubmitted(boolean value) {
    this.isSubmitted = value;
}
public boolean isIsSubmitted() {
    return this.isSubmitted;
}
private boolean isLate;

public void setIsLate(boolean value) {
    this.isLate = value;
}
public boolean isIsLate() {
    return this.isLate;
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
   
   }
