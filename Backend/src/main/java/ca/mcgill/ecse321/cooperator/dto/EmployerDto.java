package ca.mcgill.ecse321.cooperator.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;

public class EmployerDto {
	
	
	   private List<CoopTermDto> coopTerm;
	   private String fName;
	   private String emailAddress;
	   private String userName;
	   private String password;
	   private String name;
	   private String companyName;
	   private String location;
	   
	   
	   @SuppressWarnings("unchecked")
	public EmployerDto(String name, String fName, String emailAddress, String userName,
			String password, String companyName, String location) {
		   this.emailAddress = emailAddress;
		   this.fName = fName;
		   this.name = name;
		   this.password = password;
		   this.userName  = userName;	
		   this.companyName = companyName;
		   this.location = location;
		   
	   }
	   
	   public EmployerDto(String name, String fName, String emailAddress, String userName,
				String password, String companyName, String location, List<CoopTermDto> coopTerms) {
			   this.emailAddress = emailAddress;
			   this.fName = fName;
			   this.name = name;
			   this.password = password;
			   this.userName  = userName;	
			   this.companyName = companyName;
			   this.location = location;
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
		 
		 public String getCompanyName() {
			 return companyName;
			 }
		 
		 public String getLocation() {
			 return location;
			 }


}
