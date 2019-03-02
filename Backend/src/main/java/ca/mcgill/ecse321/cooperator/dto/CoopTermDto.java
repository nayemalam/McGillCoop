package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.Set;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;
import ca.mcgill.ecse321.cooperator.model.Document;
import ca.mcgill.ecse321.cooperator.model.DocumentName;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;

public class CoopTermDto {

	private Student student;
	private Date startDate;
	private Date endDate;
	private Employer employer;
	private Set<Document> document;
	
	@SuppressWarnings("unchecked")
	public CoopTermDto(Date startDate, Date endDate, Student student, Employer employer) {
		
		this.student = student;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employer = employer;
		this.document = Collections.EMPTY_SET;
	}
	
	
	  public Student getStudent() {
	      return student;
	   }
	  
	  public Date getStartDate() {
		  return startDate;
		  }
	  
	  public Date getEndDate() {
		  return endDate;
		  }
	  
	  public Employer getEmployer() {
		   return employer;
		}
	  
	   public Set<Document> getDocument() {
		      return document;
		   }	
}
