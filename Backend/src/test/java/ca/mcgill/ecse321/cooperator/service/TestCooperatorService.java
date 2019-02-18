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
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	public void testCreateAndReadCooperatorSystem() {

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

	/**
	 * Test the inputs to the creation methods, to make sure correct input is received before
	 * storage in the database
	 */
	@Test
	public void testCreateCooperatorSystemInputs() {
		// TODO
	}
	
	/**
	 * Tests the update of a CooperatorSystem object in the database
	 */
	@Test
	public void testUpdateCooperatorSystem() {
		// TODO
	}
	
	/**
	 * Tests the deletion of a CooperatorSystem in the database
	 */
	@Test
	public void testDeleteCooperatorSystem() {
		// TODO
	}
	// ==========================================================================================
	
	// ==========================================================================================
	// Student Tests
	
	/**
	 * Test creation and persistence of a student object
	 */
	@Test
	public void testCreateAndReadStudent() {
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
	 * Test the inputs to the creation methods, to make sure correct input is received before
	 * storage in the database
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

	/**
	 * Tests the update of a student object in the database
	 */
	@Test
	public void testUpdateStudent() {
		assertEquals(0, service.getAllStudents().size());
		// Create new student object
		testCreateAndReadStudent();
		assertEquals(1,service.getAllStudents().size());
		List<Student> studentList = service.getAllStudents();
		// Get Student that was just created.
		Student testStudent = studentList.get(0);
		String someName = "SomeNewName";
		testStudent.setFirstName(someName);
		
		try {
			service.updateStudent(testStudent);
		} catch (Exception e) {
			fail();
		}
		
		Student savedStudent = service.getStudent(testStudent.getUserID());
		assertEquals(someName, savedStudent.getFirstName());
	}
	/**
	 * Tests the deletion of a student in the database
	 */
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
	public void testCreateAndReadEmployer() {
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
	
	/**
	 * Test the inputs to the creation methods, to make sure correct input is received before
	 * storage in the database
	 */
	@Test
	public void testCreateEmployerInputs() {
		assertEquals(0, service.getAllEmployers().size());
		String[] errorMessages = { "Please enter a valid User ID", "Person name cannot be empty!",
				"Email Address cannot be empty!", "Username cannot be empty!", 
				"Please enter a valid password", "Please enter a valid company name",
				"Please enter a valid location"};
		String error = "";

		// Create input arguments to the create function
		Integer id = 123;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		String companyName = "Suh inc";
		String location = "Montreal";

		// Test ID parsing
		try {
			// Null input
			service.createEmployer(null, name, fName, emailAddress, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[0], error);

		// Test Name parsing
		try {
			// Null input
			service.createEmployer(id, null, fName, emailAddress, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);
		try {
			// Empty string input
			service.createEmployer(id, " ", fName, emailAddress, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);

		// LastName testing
		try {
			// Null input
			service.createEmployer(id, name, null, emailAddress, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);
		try {
			// Empty String
			service.createEmployer(id, name, " ", emailAddress, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);

		// Email address testing
		try {
			// Null input
			service.createEmployer(id, name, fName, null, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[2], error);
		try {
			// Empty String
			service.createEmployer(id, name, fName, " ", userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[2], error);

		// Username testing
		try {
			// Null input
			service.createEmployer(id, name, fName, emailAddress, null, password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[3], error);
		try {
			// Empty String
			service.createEmployer(id, name, fName, emailAddress, " ", password, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[3], error);

		// Password testing
		try {
			// Null input
			service.createEmployer(id, name, fName, emailAddress, userName, null, companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[4], error);
		try {
			// Empty String
			service.createEmployer(id, name, fName, emailAddress, userName, " ", companyName, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[4], error);

		// Company Name testing
		try {
			// Null input
			service.createEmployer(id, name, fName, emailAddress, userName, password, null, location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[5], error);
		try {
			// Empty string input
			service.createEmployer(id, name, fName, emailAddress, userName, password, " ", location);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[5], error);
		
		// Location testing
		try {
			// Null input
			service.createEmployer(id, name, fName, emailAddress, userName, password, companyName, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[6], error);
		try {
			// Empty String
			service.createEmployer(id, name, fName, emailAddress, userName, password, companyName, " ");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[6], error);

		// check no change in memory
		assertEquals(0, service.getAllEmployers().size());
	}
	
	/**
	 * Tests the update of an Employer object in the database
	 */
	@Test
	public void testUpdateEmployer() {
		// TODO
	}
	
	/**
	 * Tests the deletion of an Employer object from the database
	 */
	@Test
	public void testDeleteEmployer() {
		
	}
	// ==========================================================================================

	// ==========================================================================================
	// CoopAdmin tests
	
	
	// ==========================================================================================
	
	// ==========================================================================================
	// Coop Term Tests
	
	/**
	 * Test the creation and persistence of a CoopTerm object
	 */
//	@Test
//	public void testCreateAndReadCoopTerm() {
//		assertEquals(0, service.getAllCoopTerms().size());
//		assertEquals(0, service.getAllStudents().size());
//		// Store new student in the student database
//		testCreateAndReadStudent();
//		
//		Student testStudent = service.getAllStudents().get(0);
//		
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
	
	/**
	* Test the inputs to the creation methods, to make sure correct input is received before
	* storage in the database
	*/
	@Test
	public void testCoopTermInputs() {
		// TODO
	}
	
	/**
	 * Tests the update of a CoopTerm object in the database
	 */
	@Test
	public void testUpdateCoopTerm() {
		// TODO
	}
	/**
	 * Tests the deletion of a CoopTErm object from the database
	 */
	@Test
	public void testDeleteCoopTerm() {
		// TODO
	}
	
	// ==========================================================================================

	// ==========================================================================================
	// Document Tests
	
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
