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

/**
 * Controller for the backend services. Provides RESTful endpoints
 * 
 * @author Alex Masciotra
 *
 */
@CrossOrigin(origins = "*")
@RestController
public class CooperatorController {

	@Autowired
	private CooperatorService service;

	/**
	 * Post a student object to the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/students/{name}
	 * 
	 * @param name         - Last name of the Student
	 * @param fName        - First name of the Student
	 * @param emailAddress - Email address of the student
	 * @param userName     - Username of the Student
	 * @param password     - Password of the student
	 * @param studentId    - McGill student ID of the student
	 * @param program      - Student undergraduate program of study
	 * @return {@code StudentDto} Newly created student
	 */
	// POST A STUDENT
	@PostMapping(value = { "/students/{name}", "/students/{name}/" })
	public StudentDto createStudent(@PathVariable("name") String name, @RequestParam String fName,
			@RequestParam String emailAddress, @RequestParam String userName, @RequestParam String password,
			@RequestParam Integer studentId, @RequestParam String program) {
		Student student = service.createStudent(name, fName, emailAddress, userName, password, studentId, program);
		return convertToDto(student);

	}

	/**
	 * Get a list of all students in the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/students/
	 * 
	 * @return Requested {@code List<StudentDto>}
	 */
	// GET THE LIST OF STUDENTS
	@GetMapping(value = { "/students/", "/students" })
	public List<StudentDto> getAllStudents() {

		List<StudentDto> studentDtos = new ArrayList<>();

		for (Student student : service.getAllStudents()) {
			studentDtos.add(convertToDto(student));
		}
		return studentDtos;
	}

	/**
	 * Post a new employer to the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/employers/{name}
	 * 
	 * @param name         - Last name of the employer
	 * @param fName        - First name of the employer
	 * @param emailAddress - Email address of the employer
	 * @param userName     - Username of the employer
	 * @param password     - password of the employer
	 * @param companyName  - Employer's company name
	 * @param location     - Company location
	 * @return Requested {@code EmployerDto} if it exists
	 */
	// POST AN EMPLOYER
	@PostMapping(value = { "/employers/{name}", "/employers/{name}/" })
	public EmployerDto createEmployer(@PathVariable("name") String name, @RequestParam String fName,
			@RequestParam String emailAddress, @RequestParam String userName, @RequestParam String password,
			@RequestParam String companyName, @RequestParam String location) {
		Employer employer = service.createEmployer(name, fName, emailAddress, userName, password, companyName,
				location);
		return convertToDto(employer);
	}

	/**
	 * Obtain a list of all employers present in the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/employers/
	 * 
	 * @return Requested {@code List<EmployerDto>}
	 */
	// GET A LIST OF EMPLOYERS
	@GetMapping(value = { "/employers/", "/employers" })
	public List<EmployerDto> getAllEmployers() {

		List<EmployerDto> employerDtos = new ArrayList<>();

		for (Employer employer : service.getAllEmployers()) {
			employerDtos.add(convertToDto(employer));
		}
		return employerDtos;
	}

	/**
	 * Post a new coopAdmin to the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/coopAdmins/{name}
	 * 
	 * @param lastName     - Last name of the coop administrator
	 * @param fName        - First name of the coop administrator
	 * @param emailAddress - Email address of CoopAdministrator
	 * @param userName     - UserName of the CoopAdministrator
	 * @param password     - Password of the CoopAdministrator
	 * @return Newly created {@code CoopAdministratorDto}
	 */
	// POST A COOPADMIN
	@PostMapping(value = { "/coopAdmins/{name}", "/coopAdmins/{name}/" })
	public CoopAdministratorDto createCoopAdmin(@PathVariable("name") String lastName, @RequestParam String fName,
			@RequestParam String emailAddress, @RequestParam String userName, @RequestParam String password) {
		CoopAdministrator coopAdmin = service.createCoopAdministrator(lastName, fName, emailAddress, userName,
				password);
		return convertToDto(coopAdmin);
	}

	/**
	 * Get a list of all CoopAdministrators in the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/coopAdmins
	 * 
	 * @return {@code List<CoopAdministratorDto>}
	 */
	// GET A LIST OF COOPADMINISTRATORS
	@GetMapping(value = { "/coopAdmins/", "/coopAdmins" })
	public List<CoopAdministratorDto> getAllCoopAdministrator() {

		List<CoopAdministratorDto> coopAdminDtos = new ArrayList<>();

		for (CoopAdministrator coopAdmin : service.getAllCoopAdministrators()) {
			coopAdminDtos.add(convertToDto(coopAdmin));
		}
		return coopAdminDtos;
	}

	// =========================================================================================
	// USE CASE 1: LOGIN
	// =========================================================================================
	/**
	 * RESTFUL end point observing Incomplete Coop Placements. Available at
	 * https://cooperator-backend-21.herokuapp.com/login
	 * 
	 * @param email    - The coopAdministrator email address
	 * @param password - The coopAdministrator password
	 * @return Boolean {@code true} if login successful, {@code false} otherwise
	 */
	@GetMapping(value = { "/login/", "/login" })
	public Boolean Login(@RequestParam String email, @RequestParam String password) {

		return service.loginSuccess(email, password);
	}

	// =========================================================================================
	// USE CASE Observe Incomplete Coop Placement
	// =========================================================================================
	/**
	 * RESTFUL end point for a coopAdministrator login. Available at
	 * https://cooperator-backend-21.herokuapp.com/incomplete
	 * 
	 * @return {@code List<StudentDto>} Students in incomplete placements
	 */
	@GetMapping(value = { "/incomplete/", "/incomplete" })
	public List<StudentDto> IncompletePlacement() {

		List<Student> studentList = service.getIncompletePlacements();
		List<StudentDto> studentDtoList = convertToStudentDto(studentList);
		return studentDtoList;
	}

	// =========================================================================================
	// USE CASE View StudentFiles
	// =========================================================================================
	/**
	 * RESTFUL end point for a coopAdministrator view student files. Available at
	 * https://cooperator-backend-21.herokuapp.com/viewStudentFiles
	 * 
	 * @param userId - The student userId
	 * @param termId - The CoopTerm Id
	 * @return List of documents, {@code List<DocumentDto>}
	 */
	@GetMapping(value = { "/viewStudentFiles/", "/viewStudentFiles" })
	public List<DocumentDto> viewStudentFiles(@RequestParam Integer userId, @RequestParam Integer termId) {
		List<Document> documents = new ArrayList<>();
		documents = service.viewStudentFiles(userId, termId);
		List<DocumentDto> documentDtoList = new ArrayList<>();
		documentDtoList = convertToDocumentDto(documents);

		return documentDtoList;

	}

	// =========================================================================================
	// USE CASE View EmployerFiles
	// =========================================================================================
	/**
	 * RESTFUL end point for a coopAdministrator view Employer files. Available at
	 * https://cooperator-backend-21.herokuapp.com/viewEmployerFiles
	 * 
	 * @param userId - The employer userId
	 * @param termId - The Coop TermId
	 * @return List of documents, {@code List<DocumentDto>}
	 */
	@GetMapping(value = { "/viewEmployerFiles/", "/viewEmployerFiles" })
	public List<DocumentDto> viewEmployerFiles(@RequestParam Integer userId, @RequestParam Integer termId) {
		List<Document> documents = new ArrayList<>();
		documents = service.viewEmployerFiles(userId, termId);
		List<DocumentDto> documentDtoList = new ArrayList<>();
		documentDtoList = convertToDocumentDto(documents);

		return documentDtoList;

	}

	// =========================================================================================
	// USE CASE Modify StudentFiles
	// =========================================================================================
	/**
	 * RESTFUL end point for a coopAdministrator edit Student files. Available at
	 * https://cooperator-backend-21.herokuapp.com/modifyStudentFiles
	 * 
	 * @param userId  - The student userId
	 * @param termId  - The CoopTerm Id
	 * @param docName - The documentName
	 * @param dueDate - The new dateDue
	 * @param dueTime - The new dueTime
	 * @param subDate - The new subDate
	 * @param subTime - The new subTime
	 * @return updated Document file {@code DocumentDto}
	 */
	@GetMapping(value = { "/modifyStudentFiles/", "modifyStudentFiles" })
	public DocumentDto modifyStudentFiles(@RequestParam Integer userId, @RequestParam Integer termId,
			@RequestParam DocumentName docName, @RequestParam Date dueDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime dueTime,
			@RequestParam Date subDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime subTime) {

		Document document_modif = new Document();

		document_modif = service.viewStudentDocument(userId, termId, docName);
		Integer docId = document_modif.getDocId();

		service.updateDocument(docName, docId, dueDate, Time.valueOf(dueTime), subDate, Time.valueOf(subTime));

		DocumentDto documentDto = convertToDto(document_modif);
		return documentDto;

	}

	// =========================================================================================
	// USE CASE Modify EmployerFiles
	// =========================================================================================
	/**
	 * RESTFUL end point for a coopAdministrator edit Employer files. Available at
	 * https://cooperator-backend-21.herokuapp.com/modifyEmployerFiles/
	 * 
	 * @param userId  - The employer userId
	 * @param termId  - The CoopTerm Id
	 * @param docName - The documentName
	 * @param dueDate - The new dateDue
	 * @param dueTime - The new dueTime
	 * @param subDate - The new subDate
	 * @param subTime - The new subTime
	 * @return updated Document file {@code DocumentDto}
	 */
	@GetMapping(value = { "/modifyEmployerFiles/", "modifyEmployerFiles" })
	public DocumentDto modifyEmployerFiles(@RequestParam Integer userId, @RequestParam Integer termId,
			@RequestParam DocumentName docName, @RequestParam Date dueDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime dueTime,
			@RequestParam Date subDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime subTime) {

		Document document_modif = new Document();

		document_modif = service.viewEmployerDocument(userId, termId, docName);
		Integer docId = document_modif.getDocId();

		service.updateDocument(docName, docId, dueDate, Time.valueOf(dueTime), subDate, Time.valueOf(subTime));

		DocumentDto documentDto = convertToDto(document_modif);
		return documentDto;

	}

	// =========================================================================================
	// USE CASE Get Statistics, number of students in Coop
	// =========================================================================================

	/**
	 * Gets the number of students that are currently in a CoopTerm. Available at
	 * https://cooperator-backend-21.herokuapp.com/stats/studentsInCoop
	 * 
	 * @param semDate - Date that falls within the semester's start and end date
	 * @return number of students in current coopTerms, {@code Integer}
	 */
	@GetMapping(value = { "/stats/studentsInCoop/", "/stats/studentsInCoop" })
	public Integer studentsInCoop(@RequestParam Date semDate) {

		Integer students = service.getStatisticsBySemester(semDate).getNumberAtWork();

		return students;

	}

	// =========================================================================================
	// USE CASE Get Statistics, number of students in their first coop
	// =========================================================================================

	/**
	 * Gets students in their first coop term. Available at
	 * https://cooperator-backend-21.herokuapp.com/stats/studentsInFirst
	 * 
	 * @param semDate - Date that falls within the semester's start and end date
	 * @return List of students in their first Term, {@code List<StudentDto>}
	 */
	@GetMapping(value = { "/stats/studentsInFirst/", "/stats/studentsInFirst" })
	public List<StudentDto> studentsInFirst(@RequestParam Date semDate) {

		List<Student> students = service.getStatisticsBySemester(semDate).getFirstWorkTerm();

		List<StudentDto> dtoStudents = convertToStudentDto(students);
		return dtoStudents;

	}

	// =========================================================================================
	// USE CASE Get Statistics, number of students in their first coop
	// =========================================================================================

	/**
	 * Gets students in their second coop term. Available at
	 * https://cooperator-backend-21.herokuapp.com/stats/studentsInSecond/
	 * 
	 * @param semDate - Date that falls within the semester's start and end date
	 * @return List of students in their Second Term {@code List<StudentDto>}
	 */
	@GetMapping(value = { "/stats/studentsInSecond/", "/stats/studentsInSecond" })
	public List<StudentDto> studentsInSecond(@RequestParam Date semDate) {

		List<Student> students = service.getStatisticsBySemester(semDate).getSecondWorkTerm();

		List<StudentDto> dtoStudents = convertToStudentDto(students);
		return dtoStudents;

	}

	// =========================================================================================
	// USE CASE Get Statistics, number of students in their third coop
	// =========================================================================================

	/**
	 * Gets students in their third coop term. Available at
	 * https://cooperator-backend-21.herokuapp.com/stats/studentsInThird/
	 * 
	 * @param semDate - Date that falls within the semester's start and end date
	 * @return List of students in their Third Term, {@code List<StudentDto>}
	 */
	@GetMapping(value = { "/stats/studentsInThird/", "/stats/studentsInThird" })
	public List<StudentDto> studentsInThird(@RequestParam Date semDate) {

		List<Student> students = service.getStatisticsBySemester(semDate).getThirdWorkTerm();

		List<StudentDto> dtoStudents = convertToStudentDto(students);
		return dtoStudents;

	}

	/**
	 * Posts CoopTerm from the REST endpoint. Available at URL
	 * https://cooperator-backend-21.herokuapp.com/coopterms
	 * 
	 * @param startDate    - Date of the start of the semester
	 * @param endDate      - Date of the end of the semester
	 * @param studLastName - Student last name
	 * @param lastName     - Employer last name
	 * @return {@code CoopTermDto} created CoopTerm
	 */
	// POST A COOPTERM
	@PostMapping(value = { "/coopterms/", "/coopterms" })
	public CoopTermDto createCoopTerm(@RequestParam Date startDate, @RequestParam Date endDate,
			@RequestParam String studLastName, @RequestParam String lastName) {

		Student student = service.getStudentByName(studLastName);
		Employer employer = service.getEmployerByLastName(lastName);

		// Create a coopTerm and convert it to a Dto
		CoopTerm coopTerm = service.createCoopTerm(startDate, endDate, student, employer);
		Integer termId = coopTerm.getTermId();

		return convertToDto(coopTerm, student, employer, termId);
	}

	/**
	 * Get All coopterms from the backend. Available at
	 * https://cooperator-backend-21.herokuapp.com/coopterms
	 * 
	 * @return {@code List<CoopTermDto>} List of requested CoopTerms
	 */
	// GET A LIST OF COOPTERMS
	@GetMapping(value = { "/coopterms/", "/coopterms" })
	public List<CoopTermDto> getAllCoopTerms() {

		List<CoopTermDto> coopTermDtos = new ArrayList<>();

		for (CoopTerm coopTerm : service.getAllCoopTerms()) {
			coopTermDtos.add(convertToDto(coopTerm));
		}
		return coopTermDtos;
	}

	/**
	 * Post a new document from the REST end point Available at
	 * https://cooperator-backend-21.herokuapp.com/documents/{docname}
	 * 
	 * @param docName - Name if the document, according to the DocumentName
	 *                enumeration
	 * @param dueDate - Deadline date
	 * @param dueTime - Deadline time
	 * @param subDate - Student submission date
	 * @param subTime - student submission time
	 * @param termId  - TermID of the associated CoopTerm
	 * @return {@code DocumentDto} Posted Document
	 */
	// POST A DOCUMENT
	@PostMapping(value = { "/documents/{docName}", "/documents/{docName}/" })
	public DocumentDto createDocument(@PathVariable("docName") DocumentName docName, @RequestParam Date dueDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime dueTime,
			@RequestParam Date subDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime subTime,
			@RequestParam Integer termId) {

		CoopTerm coopTerm = service.getCoopTerm(termId);
		Document document = service.createDocument(docName, dueDate, Time.valueOf(dueTime), subDate,
				Time.valueOf(subTime), coopTerm);

		return convertToDto(document);

	}

	/**
	 * Get a list of all documents in the database Available at
	 * https://cooperator-backend-21.herokuapp.com/documents
	 * 
	 * @return {@code List<DocumentDto>} List of all Documents in the Database
	 */
	// GET A LIST OF ALL THE DOCUMENTS
	@GetMapping(value = { "/documents/", "/documents" })
	public List<DocumentDto> getAllDocuments() {

		List<DocumentDto> documentDtos = new ArrayList<>();

		for (Document document : service.getAllDocuments()) {
			documentDtos.add(convertToDto(document));
		}
		return documentDtos;
	}

	// ===============================================================================
	// METHODS RELATED TO CONVERTING OBJECTS TO DTO
	// ===============================================================================
	/**
	 * Method used to convert the database Student to a DTO
	 * 
	 * @param student - Student to convert
	 * @return DTO object of the student
	 */
	private StudentDto convertToDto(Student student) {
		if (student == null) {
			throw new IllegalArgumentException("There is no such Student!");
		}
		StudentDto studentDto = new StudentDto(student.getLastName(), student.getFirstName(), student.getEmailAddress(),
				student.getUserName(), student.getPassword(), student.getStudentId(), student.getProgram());
		studentDto.setCoopTerms(createCoopTermDtosForStudent(student));
		return studentDto;
	}

	/**
	 * Method used to convert the database Employer to a DTO
	 * 
	 * @param employer - Employer to convert
	 * @return DTO object of the employer
	 */
	private EmployerDto convertToDto(Employer employer) {

		EmployerDto employerDto = new EmployerDto(employer.getLastName(), employer.getFirstName(),
				employer.getEmailAddress(), employer.getUserName(), employer.getPassword(), employer.getCompanyName(),
				employer.getLocation());
		employerDto.setCoopTerms(createCoopTermDtosForEmployer(employer));
		return employerDto;
	}

	/**
	 * Method used to convert the database CoopAdministrator to a DTO
	 * 
	 * @param coopAdmin - CoopAdministrator to convert
	 * @return DTO object of the coopAdmin
	 */
	private CoopAdministratorDto convertToDto(CoopAdministrator coopAdmin) {

		CoopAdministratorDto coopAdministratorDto = new CoopAdministratorDto(coopAdmin.getLastName(),
				coopAdmin.getFirstName(), coopAdmin.getEmailAddress(), coopAdmin.getUserName(),
				coopAdmin.getPassword());
		return coopAdministratorDto;
	}

	/**
	 * Method used to convert the CoopTerm database object to a DTO
	 * 
	 * @param coopTerm - CoopTerm object to convert
	 * @param student  - Student associated to the CoopTerm
	 * @param employer - Employer associated to the CoopTerm
	 * @param termId   - TermId of the CoopTerm
	 * @return DTO of the CoopTerm
	 */
	private CoopTermDto convertToDto(CoopTerm coopTerm, Student student, Employer employer, Integer termId) {

		StudentDto studentDto = convertToDto(student);
		EmployerDto employerDto = convertToDto(employer);
		CoopTermDto coopTermDto = new CoopTermDto(termId, coopTerm.getStartDate(), coopTerm.getEndDate());// ,
																											// studentDto,
																											// employerDto);
		return coopTermDto;
	}

	/**
	 * Method used to convert the CoopTerm database object to a DTO
	 * 
	 * @param coopTerm
	 * @return DTO of the CoopTerm
	 */
	private CoopTermDto convertToDto(CoopTerm coopTerm) {
		if (coopTerm == null) {
			throw new IllegalArgumentException("There is no such Event!");
		}
		CoopTermDto coopTermDto = new CoopTermDto(coopTerm.getTermId(), coopTerm.getStartDate(), coopTerm.getEndDate());
		coopTermDto.setDocument(createDocumentDtosForCoopTerm(coopTerm));
		return coopTermDto;
	}

	/**
	 * Method used to convert the Document database object to a DTO
	 * 
	 * @param document
	 * @return The DTO object of the document
	 */
	private DocumentDto convertToDto(Document document) {

		DocumentDto documentDto = new DocumentDto(document.getDocName(), document.getDueDate(), document.getDueTime(),
				document.getSubDate(), document.getSubTime()); // , convertToDto(document.getCoopTerm()));
		return documentDto;
	}

	/**
	 * Method used to create a CoopTerm DTO for a student
	 * 
	 * @param student - Student for which to create a CoopTermDto
	 * @return {@code List<CoopTermDto}
	 */
	private List<CoopTermDto> createCoopTermDtosForStudent(Student student) {
		List<CoopTerm> coopTerms = service.getAllCoopTerms();
		List<CoopTermDto> coopTermList = new ArrayList<>();
		for (CoopTerm coopTerm : coopTerms) {
			if (coopTerm.getStudent().getUserID().equals(student.getUserID())) {
				coopTermList.add(convertToDto(coopTerm));
			}
		}
		return coopTermList;
	}

	/**
	 * Method used to create CoopTermDto's for an Employer
	 * 
	 * @param employer - Employer for which to create CoopTerms
	 * @return {@code List<CoopTermDto>}
	 */
	private List<CoopTermDto> createCoopTermDtosForEmployer(Employer employer) {
		List<CoopTerm> coopTerms = service.getAllCoopTerms();
		List<CoopTermDto> coopTermList = new ArrayList<>();

		for (CoopTerm coopTerm : coopTerms) {
			if (coopTerm.getEmployer().getUserID().equals(employer.getUserID())) {
				coopTermList.add(convertToDto(coopTerm));
			}
		}
		return coopTermList;
	}

	/**
	 * Method used to create document DTO's for a CoopTerm
	 * 
	 * @param coopTerm - CoopTerm for which to create DTO's
	 * @return {@code List<DocumentDto>}
	 */
	private List<DocumentDto> createDocumentDtosForCoopTerm(CoopTerm coopTerm) {
		List<Document> documentList = service.getAllDocuments();
		List<DocumentDto> documentDtoList = new ArrayList<>();

		for (Document document : documentList) {
			if (document.getCoopTerm().getTermId().equals(coopTerm.getTermId())) {
				documentDtoList.add(convertToDto(document));
			}
		}
		return documentDtoList;
	}

	/**
	 * Method used to convert a list of Student object to DTO's
	 * 
	 * @param studentList - List
	 * @return {@code List<StudentDto>}
	 */
	// Method for converting a student list to a studentDto list
	private List<StudentDto> convertToStudentDto(List<Student> studentList) {

		List<StudentDto> studentDtoList = new ArrayList<>();
		for (Student student : studentList) {
			studentDtoList.add(convertToDto(student));
		}
		return studentDtoList;
	}

	/**
	 * Method used to convert a list of Documents to DTO's
	 * 
	 * @param documentList - List of Documents to convert
	 * @return {@code List<DocumentDto><}
	 */
	// Method for converting a Document list to a documentDto list
	private List<DocumentDto> convertToDocumentDto(List<Document> documentList) {

		List<DocumentDto> documentDtoList = new ArrayList<>();
		for (Document document : documentList) {
			documentDtoList.add(convertToDto(document));
		}
		return documentDtoList;
	}

}
