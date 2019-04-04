package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;
import ca.mcgill.ecse321.cooperator.model.Document;
import ca.mcgill.ecse321.cooperator.model.DocumentName;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;

public class CoopTermDto {

	//private StudentDto student;
	private Integer termId;
	private Date startDate;
	private Date endDate;
	private String companyName;
	private Integer studentId;
	private List<DocumentDto> document;
	
	//@SuppressWarnings("unchecked")
	public CoopTermDto(Integer termId, Date startDate, Date endDate, String companyname, Integer studentid) { 
		
		this.termId = termId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.companyName = companyname;
		this.studentId = studentid;
	}
	
public CoopTermDto(int termId, Date startDate, Date endDate, StudentDto studentDto, EmployerDto employerDto, List<DocumentDto> doc) {
		
		this.termId = termId;
		//this.student = studentDto;
		this.startDate = startDate;
		this.endDate = endDate;
		//this.employer = employerDto;
		this.document = doc;
	}
	
	 public int getTermId() {
		 return termId;
	 }
	 public String getCompanyName() {
		return this.companyName;
	 }
	  
	 public Integer getStudentId() {
		return this.studentId; 
		 
	 }
	  public Date getStartDate() {
		  return startDate;
		  }
	  
	  public Date getEndDate() {
		  return endDate;
		  }
	  
	   public List<DocumentDto> getDocument() {
		      return document;
		   }	
	   
	   public void setDocument(List<DocumentDto> documents) {
			this.document =  documents;
		} 
}
