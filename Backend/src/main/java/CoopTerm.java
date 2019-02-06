import ca.mcgill.ecse321.cooperator.model.CoOperatorSystem;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import java.util.Set;
import javax.persistence.Entity;

@Entity
public class CoopTerm {
	private Date endDate;

	private void setEndDate(Date value) {
		this.endDate = value;
	}

	private Date getEndDate() {
		return this.endDate;
	}

	private Date startDate;

private void setStartDate(Datevalue) {
		this.startDate = value;
	}

private DategetStartDate() {
		return this.startDate;
	}

	private Student student;

	@ManyToOne(optional = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	private CoOperatorSystem coOperatorSystem;

	@ManyToOne(optional = false)
	public CoOperatorSystem getCoOperatorSystem() {
		return this.coOperatorSystem;
	}

	public void setCoOperatorSystem(CoOperatorSystem coOperatorSystem) {
		this.coOperatorSystem = coOperatorSystem;
	}

	private Set<Document> document;

	@OneToMany(mappedBy = "coopTerm", cascade = { CascadeType.ALL })
	public Set<Document> getDocument() {
		return this.document;
	}

	public void setDocument(Set<Document> documents) {
		this.document = documents;
	}

	private Employer employer;

	@ManyToOne(optional = false)
	public Employer getEmployer() {
		return this.employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

}
