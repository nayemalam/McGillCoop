package ca.mcgill.ecse321.cooperator.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
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
@CrossOrigin
@RestController
public class CooperatorController {

	@Autowired
	private CooperatorService service;

	@GetMapping(value = { "/students/update/", "/students/update" })
	public void getStudentsExternal() throws IOException {
		String resourceUrl = "https://ecse321-w2019-g01-backend.herokuapp.com/external/students";
		RestTemplate restTemplate = new RestTemplate();
		String jsonString = restTemplate.getForObject(resourceUrl, String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj;

		actualObj = mapper.readTree(jsonString);

		List<String> studIdList = new ArrayList<String>();
		// Iterate over Student objects returned to obtain student ID's
		Iterator<JsonNode> elems = actualObj.elements();
		while (elems.hasNext()) {
			// Get actual student object
			JsonNode child = elems.next();
			String studId = child.get("student_id").asText();
			studIdList.add(studId);
		}
		//Empty repositories
		service.deleteAllDocuments();
		service.deleteAllCoopTerms();
		service.deleteAllEmployers();
		service.deleteAllStudents();
		
//		// Once we have the student ID's, verify if these students exist in our database
//		// already
//		List<String> stusToAdd = new ArrayList<String>();
//		for (int i = 0; i < studIdList.size(); i++) {
//			String currId = studIdList.get(i);
//			// Make sure that the ID is right length to search
//			String currId2 = currId.substring(0, 8);
//			Boolean exists = service.studentExistsByStudentId(Integer.parseInt(currId2));
//			if (!exists) {
//				stusToAdd.add(currId);
//			}
//		}

		// From these student id's, we can further call the REST api to obtain more
		// information about the student and the internship
		String newUrl;
		
		for (int i = 0; i < studIdList.size(); i++) {
			newUrl = resourceUrl + "/" + studIdList.get(i);
			jsonString = restTemplate.getForObject(newUrl, String.class);
			actualObj = mapper.readTree(jsonString);

			// Begin by parsing for student information.
			// Need First name, Last Name, email address,
			String stuFirstName = actualObj.get("first_name").asText();
			String stuLastName = actualObj.get("last_name").asText();
			String stuMcGillId = actualObj.get("student_id").asText();
			String stuEmail = actualObj.get("email").asText();

			// Some hardcoded strings not available from the team 1 API
			String stuPass = stuLastName + "1";
			String stuUName = stuLastName + stuFirstName;
			String stuProgram = "ECSE";
			// Remove one number from the ID
			Integer stuIdInt = Integer.parseInt(stuMcGillId.substring(0, 8));
			Student stu = service.createStudent(stuLastName, stuFirstName, stuEmail, stuUName, stuPass, stuIdInt,
					stuProgram);
			// Obtain information about employer
			JsonNode internship = actualObj.get("internship");

			// Iterate over internship terms.
			Iterator<JsonNode> iter = internship.elements();
			while (iter.hasNext()) {
				JsonNode term = iter.next();
				JsonNode applicationForm = term.get("application_form");
				if (!applicationForm.equals(null)) {
					
					// Create employer
					String employer = applicationForm.get("employer").asText();
					String empFirstName = employer.split(" ", 0)[0];
					String empLastName = employer.split(" ", 0)[1];
					String empCompany = applicationForm.get("company").asText();
					String empEmail = applicationForm.get("employer_email").asText();
					String empUName = empLastName + empFirstName;
					String empPass = empLastName + "1";
					String empLocation = applicationForm.get("location").asText();
					Employer emp = service.createEmployer(empLastName, empFirstName, empEmail, empUName, empPass,
							empCompany, empLocation);
					
					// Create CoopTerm
					Date startDate = Date.valueOf(applicationForm.get("start_date").asText());
					Date endDate = Date.valueOf(applicationForm.get("end_date").asText());
					CoopTerm coop = service.createCoopTerm(startDate, endDate, stu, emp);
					
					// Create documents associated to internship by iterating over them
					JsonNode docs = term.get("document");
					Iterator<JsonNode> docIter = docs.elements();
					
					while(docIter.hasNext()) {
						JsonNode doc = docIter.next();
						String docType = doc.get("document_type").asText();
						DocumentName docName = DocumentName.finalReport;
						
						if(docType.contentEquals("EVALUATION")) {
							docName = DocumentName.courseEvaluation;
						} else if(docType.contentEquals("TECHNICAL_REPORT")) {
							docName = DocumentName.finalReport;
						} else {
							docName = DocumentName.taskDescription;
						}

						String submissionTimestamp = doc.get("submission_date_time").asText();
						String subTimeStampDate = submissionTimestamp.substring(0,10);
						Date subDate = Date.valueOf(subTimeStampDate);
						String subTimeStamp = submissionTimestamp.split("T", 0)[1];
						String subTimeStamp2 = subTimeStamp.substring(0,8);
						Time subTime = Time.valueOf(subTimeStamp2);
						Date dueDate = subDate;
						String externalDocId = doc.get("id").asText();
						
						@SuppressWarnings("unused")
						Document docuum = service.createDocument(docName, dueDate, subTime, subDate, subTime, coop, externalDocId);
					}
				}
			}

		}
	}

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
	 * Delete a student object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/students/{userId}
	 * 
	 * @param userId - UserId of student
	 *
	 */
	// Delete A STUDENT
	@DeleteMapping(value = { "/students/{userId}", "/students/{userId}/" })
	public void deleteStudent(@PathVariable("userId") Integer userId) {
		service.deleteStudent(userId);
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
	 * Get a student object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/students/{userId}
	 * 
	 * @param userId - UserId of student
	 * @return Student requested - Student DTO
	 *
	 */
	// Get A STUDENT
	@GetMapping(value = { "/students/{userId}", "/students/{userId}/" })
	public StudentDto getStudent(@PathVariable("userId") Integer userId) {
		Student student = service.getStudent(userId);
		return convertToDto(student);
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
	 * Delete a Employer object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/employers/{userId}
	 * 
	 * @param userId - UserId of employer
	 *
	 */
	// Delete An Employer
	@DeleteMapping(value = { "/employers/{userId}", "/employers/{userId}/" })
	public void deleteEmployer(@PathVariable("userId") Integer userId) {
		service.deleteEmployer(userId);
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
	 * Get a employer object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/employers/{userId}
	 * 
	 * @param userId - UserId of employer
	 * @return Employer requested - Employer DTO
	 *
	 */
	// Get An Employer
	@GetMapping(value = { "/employers/{userId}", "/employers/{userId}/" })
	public EmployerDto getEmployer(@PathVariable("userId") Integer userId) {
		Employer employer = service.getEmployer(userId);
		return convertToDto(employer);
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
	 * Delete a CoopAdmin object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/coopAdmins/{userId}
	 * 
	 * @param userId - UserId of CoopAdmin
	 *
	 */
	// Delete An CoopAdmin
	@DeleteMapping(value = { "/coopAdmins/{userId}", "/coopAdmins/{userId}/" })
	public void deleteCoopAdmin(@PathVariable("userId") Integer userId) {
		service.deleteCoopAdministrator(userId);
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

	/**
	 * Get a coopAdmin object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/coopAdmins/{userId}
	 * 
	 * @param userId - UserId of coopAdmin
	 * @return CoopAdmin requested - CoopAdmin DTO
	 *
	 */
	// Get An CoopAdmin
	@GetMapping(value = { "/coopAdmins/{userId}", "/coopAdmins/{userId}/" })
	public CoopAdministratorDto getCoopAdmin(@PathVariable("userId") Integer userId) {
		CoopAdministrator coopAdmin = service.getCoopAdministrator(userId);
		return convertToDto(coopAdmin);
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

	// controller method to get the list of coopTerms of a given system user
	@GetMapping(value = { "/viewStudentTerms/", "/viewStudentTerms" })
	public List<CoopTermDto> viewStudentTerms(@RequestParam Integer userId) {
		List<CoopTerm> coopTerms = new ArrayList<>();
		coopTerms = service.getCoopTermByUserId(userId);
		List<CoopTermDto> coopTermDtoList = new ArrayList<>();
		coopTermDtoList = convertToDto(coopTerms);
		return coopTermDtoList;

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

	/**
	 * Get a employer object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/employers/term/{termId}
	 * 
	 * @param termId - termId of employer
	 * @return Employer requested - Employer DTO
	 *
	 */
	// Get An Employer
	@GetMapping(value = { "/employers/term/{termId}", "/employers/term/{termId}/" })
	public EmployerDto getEmployerByTerm(@PathVariable("termId") Integer termId) {
		CoopTerm coopTerm = service.getCoopTerm(termId);
		Integer temp = coopTerm.getEmployer().getUserID();
		Employer employer = service.getEmployer(temp);

		return convertToDto(employer);

	}

	/**
	 * Get a student object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/employers/term/{termId}
	 * 
	 * @param termId - termId of student
	 * @return Student requested - Student DTO
	 *
	 */

	// Get A Student
	@GetMapping(value = { "/students/term/{termId}", "/employers/term/{termId}/" })
	public StudentDto getStudentByTerm(@PathVariable("termId") Integer termId) {
		CoopTerm coopTerm = service.getCoopTerm(termId);
		Integer temp = coopTerm.getStudent().getUserID();
		Student student = service.getStudent(temp);

		return convertToDto(student);
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
	 * Delete a Coopterm object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/coopterms/{termId}
	 * 
	 * @param termId - TermId of coopTerm
	 *
	 */
	// Delete A Coopterm
	@DeleteMapping(value = { "/coopterm/{termId}", "/coopterm/{termId}/" })
	public void deleteCoopTerm(@PathVariable("termId") Integer termId) {
		service.deleteCoopTerm(termId);
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
				Time.valueOf(subTime), coopTerm, " ");

		return convertToDto(document);

	}

	/**
	 * Delete a Document object from the database. Available at
	 * https://cooperator-backend-21.herokuapp.com/documents/{docId}
	 * 
	 * @param docId - DocumentId of coopTerm
	 *
	 */
	// Delete A Document
	@DeleteMapping(value = { "/documents/{docId}", "/documents/{docId}/" })
	public void deleteDocument(@PathVariable("userId") Integer docId) {
		service.deleteDocument(docId);
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
				student.getUserName(), student.getPassword(), student.getStudentId(), student.getProgram(),
				student.getUserID());
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
				employer.getLocation(), employer.getUserID());
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
				coopAdmin.getFirstName(), coopAdmin.getEmailAddress(), coopAdmin.getUserName(), coopAdmin.getPassword(),
				coopAdmin.getUserID());
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
		CoopTermDto coopTermDto = new CoopTermDto(termId, coopTerm.getStartDate(), coopTerm.getEndDate(),
				employer.getCompanyName(), student.getStudentId(), coopTerm.getSemester());// ,
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
		CoopTermDto coopTermDto = new CoopTermDto(coopTerm.getTermId(), coopTerm.getStartDate(), coopTerm.getEndDate(),
				coopTerm.getEmployer().getCompanyName(), coopTerm.getStudent().getStudentId(), coopTerm.getSemester());
		coopTermDto.setDocument(createDocumentDtosForCoopTerm(coopTerm));
		return coopTermDto;
	}

	private List<CoopTermDto> convertToDto(List<CoopTerm> coopTerms) {
		List<CoopTermDto> termsDto = new ArrayList<CoopTermDto>();
		for (CoopTerm term : coopTerms) {
			termsDto.add(convertToDto(term));
		}
		return termsDto;
	}

	/**
	 * Method used to convert the Document database object to a DTO
	 * 
	 * @param document
	 * @return The DTO object of the document
	 */
	private DocumentDto convertToDto(Document document) {

		DocumentDto documentDto = new DocumentDto(document.getDocName(), document.getDueDate(), document.getDueTime(),
				document.getSubDate(), document.getSubTime(), document.getDocId(), document.getExternalDocId()); // ,
																					// convertToDto(document.getCoopTerm()));
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
	private List<DocumentDto> createDocumentDtosForCoopTerm(CoopTerm coopTermIn) {
		List<DocumentDto> documentDtoList = new ArrayList<>();
		if (service.coopTermExists(coopTermIn.getTermId())) {
			CoopTerm coopTerm = service.getCoopTerm(coopTermIn.getTermId());
			Set<Document> documentList = coopTerm.getDocument();

			for (Document document : documentList) {
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
