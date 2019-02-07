package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import ca.mcgill.ecse321.cooperator.model.CoOperatorSystem;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
public class Document{
private CoOperatorSystem coOperatorSystem;

@ManyToOne(optional=false)
public CoOperatorSystem getCoOperatorSystem() {
   return this.coOperatorSystem;
}

public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
   this.coOperatorSystem = coOperatorSystem;
}

private Boolean isSubmitted;

private void setIsSubmitted(Boolean value) {
   this.isSubmitted = value;
}

private Boolean getIsSubmitted() {
   return this.isSubmitted;
}

/**
 * <pre>
 *           1..1     1..1
 * Document ------------------------> Date
 *           &lt;       dateSubmitted
 * </pre>
 */
private Date dateSubmitted;

private void setDateSubmitted(Date value) {
   this.dateSubmitted = value;
}

private Date getDateSubmitted() {
   return this.dateSubmitted;
}

private Boolean isLate;

private void setIsLate(Boolean value) {
   this.isLate = value;
}

private Boolean getIsLate() {
   return this.isLate;
}

/**
 * <pre>
 *           1..1     1..1
 * Document ------------------------> Date
 *           &lt;       deadlineDate
 * </pre>
 */
private Date deadlineDate;

private void setDeadlineDate(Date value) {
   this.deadlineDate = value;
}

private Date getDeadlineDate() {
   return this.deadlineDate;
}

/**
 * <pre>
 *           1..1     1..1
 * Document ------------------------> DocName
 *           &lt;       documentName
 * </pre>
 */
private DocName documentName;

public void setDocumentName(DocName value) {
   this.documentName = value;
}

public DocName getDocumentName() {
   return this.documentName;
}

private CoopTerm coopTerm;

@OneToOne(optional=false)
public CoopTerm getCoopTerm() {
   return this.coopTerm;
}

public void setCoopTerm(CoopTerm coopTerm) {
   this.coopTerm = coopTerm;
}

}
