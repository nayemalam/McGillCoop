package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Date;


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
@Id
public DocumentName getDocName() {
    return this.docName;
}
}
