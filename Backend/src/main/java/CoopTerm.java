import javax.persistence.Entity;
import ca.mcgill.ecse321.cooperator.model.CoOperatorSystem;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@Entity
public class CoopTerm {
	private CoOperatorSystem coOperatorSystem;

	@ManyToOne(optional = false)
	public CoOperatorSystem getCoOperatorSystem() {
		return this.coOperatorSystem;
	}

	public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
		this.coOperatorSystem = coOperatorSystem;
	}

	private Student student;

	@ManyToOne(optional = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	private Document document;

	@OneToOne(mappedBy = "coopTerm", cascade = { CascadeType.ALL }, optional = false)
	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	private Employer employer;

	@ManyToOne(optional = false)
	public Employer getEmployer() {
		return this.employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	private Date startDate;

	private void setStartDate(Date value) {
		this.startDate = value;
	}

	private Date getStartDate() {
		return this.startDate;
	}

	private Date endDate;

	private void setEndDate(Date value) {
		this.endDate = value;
	}

	private Date getEndDate() {
		return this.endDate;
	}
}
