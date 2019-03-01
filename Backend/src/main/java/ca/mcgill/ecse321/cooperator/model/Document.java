package ca.mcgill.ecse321.cooperator.model;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Document{
private Date dueDate;

public void setDueDate(Date value) {
this.dueDate = value;
}
public Date getDueDate() {
return this.dueDate;
}
private Date subDate;

public void setSubDate(Date value) {
this.subDate = value;
}
public Date getSubDate() {
return this.subDate;
}
private Time dueTime;

public void setDueTime(Time value) {
this.dueTime = value;
}
public Time getDueTime() {
return this.dueTime;
}
private Time subTime;

public void setSubTime(Time value) {
this.subTime = value;
}
public Time getSubTime() {
return this.subTime;
}
private Integer docId;

public void setDocId(Integer value) {
this.docId = value;
}
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Integer getDocId() {
return this.docId;
}
   
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
}
