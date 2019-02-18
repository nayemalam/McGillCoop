package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;
import ca.mcgill.ecse321.cooperator.model.SystemUser;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.model.CoopAdministrator;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.CooperatorSystem;
import ca.mcgill.ecse321.cooperator.model.Document;
import ca.mcgill.ecse321.cooperator.model.DocumentName;
import ca.mcgill.ecse321.cooperator.dao.CooperatorSystemRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopAdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopTermRepository;
import ca.mcgill.ecse321.cooperator.dao.DocumentRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.dao.SystemUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorService {

	@Autowired
	private CooperatorService service;

	@Autowired
	private CooperatorSystemRepository cooperatorSystemRepository;
	@Autowired
	private CoopTermRepository coopTermRepository;
	@Autowired
	private SystemUserRepository systemUserRepository;
	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private CoopAdministratorRepository coopAdministratorRepository;
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private StudentRepository studentRepository;

	@Before
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		documentRepository.deleteAll();
		// Then we can clear the other tables
		systemUserRepository.deleteAll();
		coopTermRepository.deleteAll();
		cooperatorSystemRepository.deleteAll();
		coopAdministratorRepository.deleteAll();
		coopAdministratorRepository.deleteAll();
		studentRepository.deleteAll();
	}
	
	// ==========================================================================================
	// Cooperator System tests
	/**
	 *  Test creation and persistence of a CooperatorSystem object
	 */
	@Test
	public void testCreateCooperatorSystem() {

		assertEquals(0, service.getAllCooperatorSystems().size());

		Integer systemId = 420;

		try {
			service.createCooperatorSystem(systemId);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CooperatorSystem> allCooperatorSystems = service.getAllCooperatorSystems();

		assertEquals(1, allCooperatorSystems.size());
		assertEquals(systemId, allCooperatorSystems.get(0).getSystemId());
	}

	// ==========================================================================================
	
	// ==========================================================================================
	// Student tests
	/**
	 * Test creation and persistence of a student object
	 */
	@Test
	public void testCreateStudent() {
		Student testStudent;
		assertEquals(0, service.getAllStudents().size());

		Integer id = 123;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";

		try {
			testStudent = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred while creating and saving the student.
			fail();
		}

		List<Student> allUsers = service.getAllStudents();

		assertEquals(1, allUsers.size());
		assertEquals(id, allUsers.get(0).getUserID());
		assertEquals(name, allUsers.get(0).getLastName());
		assertEquals(fName, allUsers.get(0).getFirstName());
		assertEquals(emailAddress, allUsers.get(0).getEmailAddress());
		assertEquals(userName, allUsers.get(0).getUserName());
		assertEquals(password, allUsers.get(0).getPassword());
		assertEquals(studentId, allUsers.get(0).getStudentId());
		assertEquals(program, allUsers.get(0).getProgram());
		testStudent = null;
	}

	/**
	 * Tests to see if input errors in the creation of the student are correctly
	 * caught
	 */
	@Test
	public void testCreateStudentInputs() {

		assertEquals(0, service.getAllStudents().size());
		String[] errorMessages = { "Please enter a valid User ID", "Person name cannot be empty!",
				"Email Address cannot be empty!", "Username cannot be empty!", 
				"Please enter a valid password", "Please enter a valid McGill Student ID",
				"Please enter a valid program"};
		String error = "";

		// Create input arguments to the create function
		Integer id = 123;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";

		// Test ID parsing
		try {
			// Null input
			service.createStudent(null, name, fName, emailAddress, userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[0], error);

		// Test Name parsing
		try {
			// Null input
			service.createStudent(id, null, fName, emailAddress, userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);
		try {
			// Empty string input
			service.createStudent(id, " ", fName, emailAddress, userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);

		// LastName testing
		try {
			// Null input
			service.createStudent(id, name, null, emailAddress, userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);
		try {
			// Empty String
			service.createStudent(id, name, " ", emailAddress, userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);

		// Email address testing
		try {
			// Null input
			service.createStudent(id, name, fName, null, userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[2], error);
		try {
			// Empty String
			service.createStudent(id, name, fName, " ", userName, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[2], error);

		// Username testing
		try {
			// Null input
			service.createStudent(id, name, fName, emailAddress, null, password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[3], error);
		try {
			// Empty String
			service.createStudent(id, name, fName, emailAddress, " ", password, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[3], error);

		// Password testing
		try {
			// Null input
			service.createStudent(id, name, fName, emailAddress, userName, null, studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[4], error);
		try {
			// Empty String
			service.createStudent(id, name, fName, emailAddress, userName, " ", studentId, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[4], error);

		// studentId testing
		try {
			// Null input
			service.createStudent(id, name, fName, emailAddress, userName, password, null, program);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[5], error);
		
		// program testing
		try {
			// Null input
			service.createStudent(id, name, fName, emailAddress, userName, password, studentId, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[6], error);
		try {
			// Empty String
			service.createStudent(id, name, fName, emailAddress, userName, password, studentId, " ");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[6], error);

		// check no change in memory
		assertEquals(0, service.getAllStudents().size());

	}

	@Test
	public void testUpdateStudent() {
		// TODO
	}
	
	@Test
	public void testDeleteStudent() {
		// TODO
	}
	// ==========================================================================================
	
	// ==========================================================================================
	// Employer tests
	
	/**
	 * Test the creation and persistence of an Employer object
	 */
	@Test
	public void testCreateEmployer() {
		Employer testEmployer;
		assertEquals(0, service.getAllEmployers().size());
		
		Integer id = 123;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		String companyName = "Suh Industries";
		String location = "Montreal";
		
		try {
			testEmployer = service.createEmployer(id, name, fName, emailAddress, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred while creating and saving the student.
			fail();
		}
		assertEquals(1, service.getAllEmployers().size());
		
		List<Employer> employerList = service.getAllEmployers();
		testEmployer = employerList.get(0);
		assertEquals(id,testEmployer.getUserID());
		assertEquals(name,testEmployer.getLastName());
		assertEquals(fName,testEmployer.getFirstName());
		assertEquals(emailAddress,testEmployer.getEmailAddress());
		assertEquals(userName,testEmployer.getUserName());
		assertEquals(password,testEmployer.getPassword());
		assertEquals(companyName,testEmployer.getCompanyName());
		assertEquals(location,testEmployer.getLocation());

	}

	@Test
	public void testCreateEmployerInputs() {
		// TODO
	}
	
	@Test
	public void testUpdateEmployer() {
		// TODO
	}
	
	@Test
	public void testDeleteEmployer() {
		
	}
	// ==========================================================================================

//	@Test
//	public void testCreateCoopTerm() {
//		assertEquals(0, service.getAllCoopTerms().size());
//		assertEquals(0, service.getAllStudents().size());
//		// Store new student in the student database
//		testCreateStudent();
//		Student testStudent = service.getAllStudents().get(0);
//		Date startDate = new Date(0);
//		System.out.println(startDate);
//		Date endDate = new Date(1);
//		Integer termId = 1;
//		
//		try {
//			service.createCoopTerm(startDate, endDate, termId, testStudent);
//		} catch (IllegalArgumentException e) {
//			// Check that no error occurred
//			fail();
//		}
//
//		List<CoopTerm> allCoopTerms = service.getAllCoopTerms();
//
//		assertEquals(1, allCoopTerms.size());
//		CoopTerm testTerm = allCoopTerms.get(0);
//		assertEquals(termId, testTerm.getTermId());
//		assertEquals(startDate, testTerm.getStartDate());
//		assertEquals(endDate, testTerm.getEndDate());
//	}

}
