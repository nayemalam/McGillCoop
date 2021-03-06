package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopTerm {
	private Student student;

	@ManyToOne(optional = false)
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

	private Integer termId;

	public void setTermId(Integer value) {
		this.termId = value;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getTermId() {
		return this.termId;
	}

	private Employer employer;

	@ManyToOne(optional = false)
	public Employer getEmployer() {
		return this.employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	private Set<Document> document;

	@OneToMany(mappedBy = "coopTerm")
	public Set<Document> getDocument() {
		return this.document;
	}

	public void setDocument(Set<Document> documents) {
		this.document = documents;
	}
	
	private String semester;
	
	public void setSemester(String _semester) {
		this.semester = _semester;
	}
	
	public String getSemester() {
		return this.semester;
	}
	

}
