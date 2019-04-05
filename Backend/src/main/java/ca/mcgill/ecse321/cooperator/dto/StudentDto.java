package ca.mcgill.ecse321.cooperator.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;

public class StudentDto {

	private List<CoopTermDto> coopTerm;
	private String program;
	private Integer studentId;
	private String fName;
	private String emailAddress;
	private String userName;
	private String password;
	private String name;
	private Integer userId;

	@SuppressWarnings("unchecked")
	public StudentDto(String name, String fName, String emailAddress, String userName, String password,
			Integer studentId, String program, Integer userId) {
		this.emailAddress = emailAddress;
		this.fName = fName;
		this.name = name;
		this.password = password;
		this.program = program;
		this.studentId = studentId;
		this.userName = userName;
		this.userId = userId;

	}

	@SuppressWarnings("unchecked")
	public StudentDto(String name, String fName, String emailAddress, String userName, String password,
			Integer studentId, String program, List<CoopTermDto> coopTerms) {
		this.emailAddress = emailAddress;
		this.fName = fName;
		this.name = name;
		this.password = password;
		this.program = program;
		this.studentId = studentId;
		this.userName = userName;
		this.coopTerm = coopTerms;

	}

	public String getFirstName() {
		return fName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getUserName() {
		return userName;
	}

	public String getLastName() {
		return name;
	}

	public List<CoopTermDto> getCoopTerm() {
		return coopTerm;
	}

	public String getProgram() {
		return program;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setCoopTerms(List<CoopTermDto> terms) {
		this.coopTerm = terms;
	}

	public Integer getUserId() {
		return userId;
	}

}
