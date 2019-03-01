package ca.mcgill.ecse321.cooperator.dto;


import java.util.Collections;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;


public class CoopAdministratorDto {
	
	private Set<Employer> employer;
	private Set<Student> student;
	private String fName;
	private String emailAddress;
	private String userName;
	private String password;
	private String name;

	
	public CoopAdministratorDto(String name, String fName, String emailAddress,
			String userName, String password) {
		
		   this.emailAddress = emailAddress;
		   this.fName = fName;
		   this.name = name;
		   this.password = password;
		   this.userName  = userName;	
		   this.employer = employer;
		   this.student = student;
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
		
		public Set<Student> getStudent() {
		      return student;
		      
		}
		
		public Set<Employer> getEmployer() {
			   return employer;
			}
}
