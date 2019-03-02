package ca.mcgill.ecse321.cooperator.controller;




import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.cooperator.dto.CoopTermDto;
import ca.mcgill.ecse321.cooperator.dto.DocumentDto;
import ca.mcgill.ecse321.cooperator.dto.EmployerDto;
import ca.mcgill.ecse321.cooperator.dto.StudentDto;
import ca.mcgill.ecse321.cooperator.model.CoopTerm;
import ca.mcgill.ecse321.cooperator.model.Document;
import ca.mcgill.ecse321.cooperator.model.DocumentName;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;



@CrossOrigin(origins = "*")
@RestController
public class CooperatorController {
	
	@Autowired
	private CooperatorService service;
	
	@PostMapping(value = { "/students/{name}", "/students/{name}/"})
	public StudentDto createStudent(@PathVariable("name") String name, @RequestParam String fName, @RequestParam String emailAddress, 
			@RequestParam String userName, @RequestParam String password, @RequestParam Integer studentId, @RequestParam String program) {
		Student student = service.createStudent(name, fName, emailAddress, userName, password, studentId, program); 
	return convertToDto(student);
	
	}
	
	@GetMapping(value = {"/students/", "/students"})
	public List<StudentDto> getAllStudents(){
		
		List<StudentDto> studentDtos = new ArrayList<>();
		
		for(Student student : service.getAllStudents()) {
			studentDtos.add(convertToDto(student));
		}
		return studentDtos;

		
	}
	
	@PostMapping(value = { "/employers/{name}", "/employers/{name}/"})
	public EmployerDto createEmployer(@PathVariable("name") String name, @RequestParam String fName, @RequestParam String emailAddress, 
			@RequestParam String userName, @RequestParam String password, @RequestParam String companyName, @RequestParam String location) {
		Employer employer = service.createEmployer(name, fName, emailAddress, userName, password, companyName, location); 
	return convertToDto(employer);
	
	}
	
	@GetMapping(value = {"/employers/", "/employers"})
	public List<EmployerDto> getAllEmployers(){
		
		List<EmployerDto> employerDtos = new ArrayList<>();
		
		for(Employer employer : service.getAllEmployers()) {
			employerDtos.add(convertToDto(employer));
		}
		return employerDtos;

		
	}
	
	
	@PostMapping(value = { "/coopterms/", "/coopterms"})
	public CoopTermDto createCoopTerm(@RequestParam(value = "date") Date startDate, @RequestParam(value = "date") Date endDate, @RequestParam Student student, 
			@RequestParam Employer employer) {
		CoopTerm coopTerm = service.createCoopTerm(startDate, endDate, student, employer);
	return convertToDto(coopTerm);
	
	}
	
	@GetMapping(value = {"/coopterms/", "/coopterms"})
	public List<CoopTermDto> getAllCoopTerms(){
		
		List<CoopTermDto> coopTermDtos = new ArrayList<>();
		
		for(CoopTerm coopTerm : service.getAllCoopTerms()) {
			coopTermDtos.add(convertToDto(coopTerm));
		}
		return coopTermDtos;

		
	}
	
	
	@PostMapping(value = { "/documents/{docName}", "/documents/{docName}/"})
	public DocumentDto createDocument(@PathVariable("docName") DocumentName docName, @RequestParam(value = "date") Date dueDate, @RequestParam Time dueTime, 
			@RequestParam(value = "date") Date subDate, @RequestParam Time subTime, @RequestParam CoopTerm coopTerm) {
		Document document = service.createDocument(docName, dueDate, dueTime, subDate, subTime, coopTerm); 
	return convertToDto(document);
	
	}
	
	@GetMapping(value = {"/documents/", "/documents"})
	public List<DocumentDto> getAllDocuments(){
		
		List<DocumentDto> documentDtos = new ArrayList<>();
		
		for(Document document : service.getAllDocuments()) {
			documentDtos.add(convertToDto(document));
		}
		return documentDtos;

		
	}

	private StudentDto convertToDto(Student student) {
		
		StudentDto studentDto = new StudentDto(student.getLastName(), student.getFirstName(), student.getEmailAddress(), student.getUserName(), student.getPassword(), student.getStudentId(), student.getProgram());
		
		// TODO Auto-generated method stub
		return studentDto;
	}
	
	private EmployerDto convertToDto(Employer employer) {
		
		EmployerDto employerDto = new EmployerDto(employer.getLastName(), employer.getFirstName(), employer.getEmailAddress(), employer.getUserName(), employer.getPassword(), employer.getCompanyName(), employer.getLocation());
		
		// TODO Auto-generated method stub
		return employerDto;
	}
	
	private CoopTermDto convertToDto(CoopTerm coopTerm) {
		
		CoopTermDto coopTermDto = new CoopTermDto(coopTerm.getStartDate(), coopTerm.getEndDate(), coopTerm.getStudent(), coopTerm.getEmployer());
		// TODO Auto-generated method stub
		return coopTermDto;
	}

	
	private DocumentDto convertToDto(Document document) {
		
		DocumentDto documentDto = new DocumentDto(document.getDocName(), document.getDueDate(), document.getDueTime(), document.getSubDate(), document.getSubTime(), document.getCoopTerm());
		// TODO Auto-generated method stub
		return documentDto;
	}



	

}

