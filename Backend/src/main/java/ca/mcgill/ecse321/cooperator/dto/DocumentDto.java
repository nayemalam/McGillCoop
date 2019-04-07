package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;
import ca.mcgill.ecse321.cooperator.model.DocumentName;

public class DocumentDto {
	private Date dueDate;
	private Date subDate;
	private Time dueTime;
	private Time subTime;
	// private CoopTermDto coopTerm;
	private DocumentName docName;
	private Integer docId;

	public DocumentDto(DocumentName docName, Date dueDate, Time dueTime, Date subDate, Time subTime, Integer docId, String externalDocId) {// ,
																														// CoopTermDto
																														// coopTerm)
																														// {
		this.docName = docName;
		this.dueDate = dueDate;
		this.dueTime = dueTime;
		this.subDate = subDate;
		this.subTime = subTime;
		this.docId = docId;
		this.externalDocId = externalDocId;
		// this.coopTerm = coopTerm;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Date getSubDate() {
		return subDate;
	}

	public Time getDueTime() {
		return dueTime;
	}

	public Time getSubTime() {
		return this.subTime;
	}

//	 public CoopTermDto getCoopTerm() {
//	      return coopTerm;
//	   }

	public DocumentName getDocName() {
		return docName;
	}

	public Integer getDocId() {
		return docId;
	}

	private String externalDocId;

	public void setExternalDocId(String externalDocId) {
		this.externalDocId = externalDocId;
	}

	public String getExternalDocId() {
		return this.externalDocId;
	}

}
