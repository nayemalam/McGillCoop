package ca.mcgill.ecse321.cooperator.controller;




import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.cooperator.dto.CoopAdministratorDto;
import ca.mcgill.ecse321.cooperator.dto.CoopTermDto;
import ca.mcgill.ecse321.cooperator.dto.DocumentDto;
import ca.mcgill.ecse321.cooperator.dto.EmployerDto;
import ca.mcgill.ecse321.cooperator.dto.StudentDto;
import ca.mcgill.ecse321.cooperator.model.CoopAdministrator;
import ca.mcgill.ecse321.cooperator.model.CoopTerm;
import ca.mcgill.ecse321.cooperator.model.Document;
import ca.mcgill.ecse321.cooperator.model.DocumentName;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.model.SystemUser;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;



@CrossOrigin(origins = "*")
@RestController
public class CooperatorController {
	
	@Autowired
	private CooperatorService service;
	
	//POST A STUDENT 
	@PostMapping(value = { "/students/{name}", "/students/{name}/"})
	public StudentDto createStudent(@PathVariable("name") String name, @RequestParam String fName, @RequestParam String emailAddress, 
			@RequestParam String userName, @RequestParam String password, @RequestParam Integer studentId, @RequestParam String program) {
		Student student = service.createStudent(name, fName, emailAddress, userName, password, studentId, program); 
	return convertToDto(student);
	
	}
	
	//GET THE LIST OF STUDENTS 
	@GetMapping(value = {"/students/", "/students"})
	public List<StudentDto> getAllStudents(){
		
		List<StudentDto> studentDtos = new ArrayList<>();
		
		for(Student student : service.getAllStudents()) {
			studentDtos.add(convertToDto(student));
		}
		return studentDtos;
	}
	
	//POST AN EMPLOYER
	@PostMapping(value = { "/employers/{name}", "/employers/{name}/"})
	public EmployerDto createEmployer(@PathVariable("name") String name, @RequestParam String fName, @RequestParam String emailAddress, 
			@RequestParam String userName, @RequestParam String password, @RequestParam String companyName, @RequestParam String location) {
		Employer employer = service.createEmployer(name, fName, emailAddress, userName, password, companyName, location); 
	return convertToDto(employer);
	}
	
	//GET A LIST OF EMPLOYERS 
	@GetMapping(value = {"/employers/", "/employers"})
	public List<EmployerDto> getAllEmployers(){
		
		List<EmployerDto> employerDtos = new ArrayList<>();
		
		for(Employer employer : service.getAllEmployers()) {
			employerDtos.add(convertToDto(employer));
		}
		return employerDtos;
	}
	
	//POST A COOPADMIN
		@PostMapping(value = { "/coopAdmins/{name}", "/coopAdmins/{name}/"})
		public CoopAdministratorDto createCoopAdmin(@PathVariable("name") String lastName, @RequestParam String fName, @RequestParam String emailAddress, 
				@RequestParam String userName, @RequestParam String password) {
			CoopAdministrator coopAdmin = service.createCoopAdministrator(lastName, fName, emailAddress, userName, password);
		return convertToDto(coopAdmin);
		}
		
	//GET A LIST OF COOPADMINISTRATORS
		@GetMapping(value = {"/coopAdmins/", "/coopAdmins"})
		public List<CoopAdministratorDto> getAllCoopAdministrator(){
			
			List<CoopAdministratorDto> coopAdminDtos = new ArrayList<>();
			
			for(CoopAdministrator coopAdmin : service.getAllCoopAdministrators()) {
				coopAdminDtos.add(convertToDto(coopAdmin));
			}
			return coopAdminDtos;
		}
	
	//=========================================================================================
	//USE CASE 1: LOGIN 
	//=========================================================================================
		/**
		 * RESTFUL end point observing Incomplete Coop Placements 
		 * 
		 * @param    - The coopAdministrator email address
		 * @param password - The coopAdministrator password
		 * @return Boolean
		 */
		@GetMapping(value = {"/login/", "/login"})
		public Boolean Login(@RequestParam String email, @RequestParam String password){
			
			return service.loginSuccess(email, password);
		}
		
		
//	//=========================================================================================
//	//USE CASE Observe Incomplete Coop Placement
//	//=========================================================================================
//		/**
//		 * RESTFUL end point for a coopAdministrator login 
//		 * 
//		 * @param email   - The coopAdministrator email address
//		 * @param password - The coopAdministrator password
//		 * @return Boolean
//		 */
//		@GetMapping(value = {"/incomplete/", "/incomplete"})
//		public boolean IncompletePlacement(@RequestParam Integer userId, @RequestParam Integer CoopTerm){
//			
//			return service.isIncomplete(userId, CoopTerm);
//		}
		
	//=========================================================================================
	//USE CASE Observe Incomplete Coop Placement
	//=========================================================================================
		/**
		 * RESTFUL end point for a coopAdministrator login 
		 * 
		 * @param email   - The coopAdministrator email address
		 * @param password - The coopAdministrator password
		 * @return Boolean
		 */
		@GetMapping(value = {"/incomplete/", "/incomplete"})
		public List<StudentDto> IncompletePlacement(){
			
			List<Student> studentList = service.getIncompletePlacements();
			List<StudentDto> studentDtoList = convertToDto(studentList);
			return studentDtoList;
		}
	
		
	
	//POST A COOPTERM 
	@PostMapping(value = { "/coopterms/", "/coopterms"})
	public CoopTermDto createCoopTerm(@RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String studLastName, 
			@RequestParam String lastName) {
		
		Student student = service.getStudentByName(studLastName);
		Employer employer = service.getEmployerByLastName(lastName);
		
		//Create a coopTerm and convert it to a Dto 
		CoopTerm coopTerm = service.createCoopTerm(startDate, endDate, student, employer);
		Integer termId = coopTerm.getTermId();
		
		return convertToDto(coopTerm, student, employer, termId);
	}
	
	
	//GET A LIST OF COOPTERMS 
	@GetMapping(value = {"/coopterms/", "/coopterms"})
	public List<CoopTermDto> getAllCoopTerms(){
		
		List<CoopTermDto> coopTermDtos = new ArrayList<>();
		
		for(CoopTerm coopTerm : service.getAllCoopTerms()) {
			coopTermDtos.add(convertToDto(coopTerm));
		}
		return coopTermDtos;		
	}
	
	//POST A DOCUMENT 
	@PostMapping(value = { "/documents/{docName}", "/documents/{docName}/"})
	public DocumentDto createDocument(@PathVariable("docName") DocumentName docName, @RequestParam Date dueDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime dueTime, 
			@RequestParam Date subDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime subTime, @RequestParam Integer termId ) {
		
		CoopTerm coopTerm = service.getCoopTerm(termId);
		Document document = service.createDocument(docName, dueDate,  Time.valueOf(dueTime), subDate,  Time.valueOf(subTime), coopTerm); 
		
	return convertToDto(document);
	
	}
	
	//GET A LIST OF ALL THE DOCUMENTS 
	@GetMapping(value = {"/documents/", "/documents"})
	public List<DocumentDto> getAllDocuments(){
		
		List<DocumentDto> documentDtos = new ArrayList<>();
		
		for(Document document : service.getAllDocuments()) {
			documentDtos.add(convertToDto(document));
		}
		return documentDtos;
	}

	
	
	//===============================================================================
	//METHODS RELATED TO CONVERTING OBJECTS TO DTO
	//===============================================================================
	private StudentDto convertToDto(Student student) {
		if (student == null) {
			throw new IllegalArgumentException("There is no such Student!");
		}
		StudentDto studentDto = new StudentDto(student.getLastName(), student.getFirstName(), student.getEmailAddress(), student.getUserName(), student.getPassword(), student.getStudentId(), student.getProgram());
		studentDto.setCoopTerms(createCoopTermDtosForStudent(student));
		return studentDto;
	}
	
	private EmployerDto convertToDto(Employer employer) {
		
		EmployerDto employerDto = new EmployerDto(employer.getLastName(), employer.getFirstName(), employer.getEmailAddress(), employer.getUserName(), employer.getPassword(), employer.getCompanyName(), employer.getLocation());
		employerDto.setCoopTerms(createCoopTermDtosForEmployer(employer));
		return employerDto;
	}
	
private CoopAdministratorDto convertToDto(CoopAdministrator coopAdmin) {
		
		CoopAdministratorDto coopAdministratorDto = new CoopAdministratorDto(coopAdmin.getLastName(), coopAdmin.getFirstName(), coopAdmin.getEmailAddress(), coopAdmin.getUserName(), coopAdmin.getPassword());
		return coopAdministratorDto;
	}
	
	private CoopTermDto convertToDto(CoopTerm coopTerm, Student student, Employer employer, Integer termId) {
		
		StudentDto studentDto = convertToDto(student);
		EmployerDto employerDto = convertToDto(employer);
		CoopTermDto coopTermDto = new CoopTermDto(termId, coopTerm.getStartDate(), coopTerm.getEndDate());//, studentDto, employerDto);
		return coopTermDto;
	}

	private CoopTermDto convertToDto(CoopTerm coopTerm) {
		if (coopTerm == null) {
			throw new IllegalArgumentException("There is no such Event!");
		}
			CoopTermDto coopTermDto = new CoopTermDto(coopTerm.getTermId(), coopTerm.getStartDate(), coopTerm.getEndDate());
			coopTermDto.setDocument(createDocumentDtosForCoopTerm(coopTerm));
			return coopTermDto;
		}
	
	
	private DocumentDto convertToDto(Document document) {
		
		DocumentDto documentDto = new DocumentDto(document.getDocName(), document.getDueDate(), document.getDueTime(), document.getSubDate(), document.getSubTime());  //, convertToDto(document.getCoopTerm()));
		return documentDto;
	}

	private List<CoopTermDto> createCoopTermDtosForStudent(Student student) {
		List<CoopTerm> coopTerms = service.getAllCoopTerms();
		List<CoopTermDto> coopTermList = new ArrayList<>();
		for (CoopTerm coopTerm : coopTerms) {
			if(coopTerm.getStudent().getUserID().equals(student.getUserID())) {
				coopTermList.add(convertToDto(coopTerm));
			}
		}
		return coopTermList;
	}
	
	private List<CoopTermDto> createCoopTermDtosForEmployer(Employer employer) {
		List<CoopTerm> coopTerms = service.getAllCoopTerms();
		List<CoopTermDto> coopTermList = new ArrayList<>();
		
		for (CoopTerm coopTerm : coopTerms) {
			if(coopTerm.getEmployer().getUserID().equals(employer.getUserID())) {
				coopTermList.add(convertToDto(coopTerm));
			}
		}
		return coopTermList;
	}
	
	private List<DocumentDto> createDocumentDtosForCoopTerm(CoopTerm coopTerm) {
		List<Document> documentList = service.getAllDocuments();
		List<DocumentDto> documentDtoList = new ArrayList<>();
		
		for (Document document : documentList) {
			if(document.getCoopTerm().getTermId().equals(coopTerm.getTermId())) {
				documentDtoList.add(convertToDto(document));
			}
		}
		return documentDtoList;
	}
	
	
	//Method for converting a student list to a studentDto list
	private List<StudentDto> convertToDto(List<Student> studentList) {
		
		List<StudentDto> studentDtoList = new ArrayList<>();
		for (Student student : studentList) {
			studentDtoList.add(convertToDto(student));
		}
		return studentDtoList;
	}
	

}

