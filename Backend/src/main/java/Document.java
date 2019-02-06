import ca.mcgill.ecse321.cooperator.model.CoOperatorSystem;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Document {
	private DocName documentName;

	public void setDocumentName(DocName value) {
		this.documentName = value;
	}

	public DocName getDocumentName() {
		return this.documentName;
	}

	private Date submissionDate;

	private void setSubmissionDate(Date value) {
		this.submissionDate = value;
	}

	private Date getSubmissionDate() {
		return this.submissionDate;
	}

	private Boolean isLate;

	private void setIsLate(Boolean value) {
		this.isLate = value;
	}

	private Boolean getIsLate() {
		return this.isLate;
	}

	private Boolean isSubmitted;

	private void setIsSubmitted(Boolean value) {
		this.isSubmitted = value;
	}

	private Boolean getIsSubmitted() {
		return this.isSubmitted;
	}

	private Date dateSubmitted;

	private void setDateSubmitted(Date value) {
		this.dateSubmitted = value;
	}

	private Date getDateSubmitted() {
		return this.dateSubmitted;
	}

	private CoOperatorSystem coOperatorSystem;

	@ManyToOne(optional = false)
	public CoOperatorSystem getCoOperatorSystem() {
		return this.coOperatorSystem;
	}

	public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
		this.coOperatorSystem = coOperatorSystem;
	}

	private CoopTerm coopTerm;

	@ManyToOne(optional = false)
	public CoopTerm getCoopTerm() {
		return this.coopTerm;
	}

	public void setCoopTerm(CoopTerm coopTerm) {
		this.coopTerm = coopTerm;
	}

}
