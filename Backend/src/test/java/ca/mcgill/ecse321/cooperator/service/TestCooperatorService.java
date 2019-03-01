package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		coopTermRepository.deleteAll();
		cooperatorSystemRepository.deleteAll();
		employerRepository.deleteAll();
		coopAdministratorRepository.deleteAll();
		studentRepository.deleteAll();
		systemUserRepository.deleteAll();
	}

//	@After
//	public void clearDatabaseAfter() {
//		// First, we clear registrations to avoid exceptions due to inconsistencies
//		documentRepository.deleteAll();
//		// Then we can clear the other tables
//		systemUserRepository.deleteAll();
//		coopTermRepository.deleteAll();
//		cooperatorSystemRepository.deleteAll();
//		coopAdministratorRepository.deleteAll();
//		coopAdministratorRepository.deleteAll();
//		studentRepository.deleteAll();
//	}

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
		String errorMessages =  "Please enter a valid systemId";
		String error = "";
		
		try {
			service.createCooperatorSystem(null);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertEquals(errorMessages, error);
	}
	
	/**
	 * Tests the update of a CooperatorSystem object in the database
	 */
//	@Test
//	public void testUpdateCooperatorSystem() {
//		// TODO
//	}
	
	/**
	 * Tests the deletion of a CooperatorSystem in the database
	 */
	@Test
	public void testDeleteCooperatorSystem() {
		assertEquals(0, service.getAllCooperatorSystems().size());

		Integer systemId = 420;
		service.createCooperatorSystem(systemId);
		assertEquals(1, service.getAllCooperatorSystems().size());

		try {
			service.deleteCooperatorSystem(systemId);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CooperatorSystem> allCooperatorSystems = service.getAllCooperatorSystems();

		assertEquals(0, allCooperatorSystems.size());
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

		List<Student> allStudents = service.getAllStudents();

		assertEquals(1, allStudents.size());
		assertEquals(id, allStudents.get(0).getUserID());
		assertEquals(name, allStudents.get(0).getLastName());
		assertEquals(fName, allStudents.get(0).getFirstName());
		assertEquals(emailAddress, allStudents.get(0).getEmailAddress());
		assertEquals(userName, allStudents.get(0).getUserName());
		assertEquals(password, allStudents.get(0).getPassword());
		assertEquals(studentId, allStudents.get(0).getStudentId());
		assertEquals(program, allStudents.get(0).getProgram());
		//testStudent = null;
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
		
		// Create new student object
		testStudent = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1,service.getAllStudents().size());

		//Updated Parameters
		String someName = "SomeNewName";
		String newpassword = "ball";

		try {
			service.updateStudent(id, someName, fName, emailAddress, userName, newpassword, studentId, program);
		} catch (Exception e) {
			fail();
		}
		
		assertEquals(1,service.getAllStudents().size());
		Student savedStudent = service.getStudent(id);
		assertEquals(newpassword, savedStudent.getPassword());
		assertEquals(someName, savedStudent.getLastName());
	}
	/**
	 * Tests the deletion of a student in the database
	 */
	@Test
	public void testDeleteStudent() {
		// TODO
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
		
		// Create new student object
		testStudent = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1,service.getAllStudents().size());
		
		// remove the student
		try {
			service.deleteStudent(id);
		} catch (IllegalArgumentException e) {
			fail();
		}

	    //exception handling when you try to remove null
		try {
			service.deleteStudent(null);
		} catch (Exception e) {
			System.out.println("Not an actual student");
		}
		
		assertEquals(0,service.getAllStudents().size());
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
		
		testEmployer = service.createEmployer(id, name, fName, emailAddress, userName, password, companyName, location);
		assertEquals(1, service.getAllEmployers().size());
		
		//Updated Parameters
		String newname = "Oscar";
		String newfName = "Macsiotra";
		String newemailAddress = "oscar@mcgill.ca";
		
		try {
			service.updateEmployer(id, newname, newfName, newemailAddress, userName, password, companyName, location);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred while creating and saving the student.
			fail();
		}
		
		
		assertEquals(1,service.getAllEmployers().size());
		Employer savedEmployer = service.getEmployer(id);
		assertEquals(newname, savedEmployer.getLastName());
		assertEquals(newfName, savedEmployer.getFirstName());
		assertEquals(newemailAddress, savedEmployer.getEmailAddress());
	}
	
	/**
	 * Tests the deletion of an Employer object from the database
	 */
	@Test
	public void testDeleteEmployer() {
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
		
		testEmployer = service.createEmployer(id, name, fName, emailAddress, userName, password, companyName, location);
		assertEquals(1, service.getAllEmployers().size());
		
		try {
			service.deleteEmployer(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred while creating and saving the student.
			fail();
		}
		
		assertEquals(0, service.getAllEmployers().size());
	}
	// ==========================================================================================

	// ==========================================================================================
	// CoopAdmin tests
	
	@Test
	public void testCreateandReadCoopAdministrator() {
		CoopAdministrator testCoopAdministrator; 
		assertEquals(0, service.getAllCoopAdministrators().size());
		
		Integer id = 1323;
		String name = "Tristan";
		String fName = "Pepper";
		String emailAddress = "Tristan@mcgill.ca";
		String userName = "pepper123";
		String password = "choco99";

		try {
			testCoopAdministrator= service.createCoopAdministrator(id, name, fName, emailAddress, userName, password);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CoopAdministrator> allCoopAdministrator = service.getAllCoopAdministrators();

		assertEquals(1, allCoopAdministrator.size());
		assertEquals(id, allCoopAdministrator.get(0).getUserID());
		assertEquals(name, allCoopAdministrator.get(0).getLastName());
		assertEquals(fName, allCoopAdministrator.get(0).getFirstName());
		assertEquals(emailAddress, allCoopAdministrator.get(0).getEmailAddress());
		assertEquals(userName, allCoopAdministrator.get(0).getUserName());
		assertEquals(password, allCoopAdministrator.get(0).getPassword());

		testCoopAdministrator = null;
	}
	
	
	
	@Test
	public void testCreateCoopAdministratorInputs() {
		assertEquals(0, service.getAllEmployers().size());
		String[] errorMessages = { "Please enter a valid User ID", "Person name cannot be empty!",
				"Email Address cannot be empty!", "Username cannot be empty!", 
				"Please enter a valid password"};
		String error = "";
		CoopAdministrator testCoopAdministrator; 
		assertEquals(0, service.getAllCoopAdministrators().size());
		
		Integer id = 1323;
		String name = "Tristan";
		String fName = "Pepper";
		String emailAddress = "Tristan@mcgill.ca";
		String userName = "pepper123";
		String password = "choco99";

		testCoopAdministrator= service.createCoopAdministrator(id, name, fName, emailAddress, userName, password);
		
		
		// Test ID parsing
				try {
					// Null input
					service.createCoopAdministrator(null, name, fName, emailAddress, userName, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[0], error);

				// Test Name parsing
				try {
					// Null input
					service.createCoopAdministrator(id, null, fName, emailAddress, userName, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[1], error);
				try {
					// Empty string input
					service.createCoopAdministrator(id, " ", fName, emailAddress, userName, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[1], error);

				// LastName testing
				try {
					// Null input
					service.createCoopAdministrator(id, name, null, emailAddress, userName, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[1], error);
				try {
					// Empty String
					service.createCoopAdministrator(id, name, " ", emailAddress, userName, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[1], error);

				// Email address testing
				try {
					// Null input
					service.createCoopAdministrator(id, name, fName, null, userName, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[2], error);
				try {
					// Empty String
					service.createCoopAdministrator(id, name, fName, " ", userName, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[2], error);

				// Username testing
				try {
					// Null input
					service.createCoopAdministrator(id, name, fName, emailAddress, null, password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[3], error);
				try {
					// Empty String
					service.createCoopAdministrator(id, name, fName, emailAddress, " ", password);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[3], error);

				// Password testing
				try {
					// Null input
					service.createCoopAdministrator(id, name, fName, emailAddress, userName, null);
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[4], error);
				try {
					// Empty String
					service.createCoopAdministrator(id, name, fName, emailAddress, userName, " ");
				} catch (IllegalArgumentException e) {
					error = e.getMessage();
				}
				assertEquals(errorMessages[4], error);
	}
	
	
	@Test
	public void testUpdateCoopAdministrator() {
		CoopAdministrator testCoopAdministrator; 
		assertEquals(0, service.getAllCoopAdministrators().size());
		
		Integer id = 1323;
		String name = "Tristan";
		String fName = "Pepper";
		String emailAddress = "Tristan@mcgill.ca";
		String userName = "pepper123";
		String password = "choco99";

		testCoopAdministrator= service.createCoopAdministrator(id, name, fName, emailAddress, userName, password);
		assertEquals(1,service.getAllCoopAdministrators().size());
		//Updated Parameters
		String newName = "thom";
		String newuserName = "ballon";
		
		try {
			service.updateCoopAdministrator(id, newName, fName, emailAddress, newuserName, password);
			} catch (IllegalArgumentException e) {
				// Check that no error occurred
				fail();
		}
		
		assertEquals(1,service.getAllCoopAdministrators().size());
		CoopAdministrator savedCoopAdmin = service.getCoopAdministrator(id);
		assertEquals(newName, savedCoopAdmin.getLastName());
		assertEquals(newuserName, savedCoopAdmin.getUserName());
		
	}
	
	
	@Test
	public void testDeleteCoopAdministrator() {
		CoopAdministrator testCoopAdministrator; 
		assertEquals(0, service.getAllCoopAdministrators().size());
		
		Integer id = 1323;
		String name = "Tristan";
		String fName = "Pepper";
		String emailAddress = "Tristan@mcgill.ca";
		String userName = "pepper123";
		String password = "choco99";

		testCoopAdministrator= service.createCoopAdministrator(id, name, fName, emailAddress, userName, password);
		assertEquals(1,service.getAllCoopAdministrators().size());
		
		try {
			service.deleteCoopAdministrator(id);
		} catch (Exception e) {
			fail();
		}
		
		assertEquals(0,service.getAllCoopAdministrators().size());
	}
	
	// ==========================================================================================
	
	// ==========================================================================================
	// Coop Term Tests
	
	/**
	 * Test the creation and persistence of a CoopTerm object
	 */
	@Test
	public void testCreateAndReadCoopTerm() {
		assertEquals(0, service.getAllCoopTerms().size());
		assertEquals(0, service.getAllStudents().size());
		assertEquals(0, service.getAllEmployers().size());
		
		//create a student 
		Student student;

		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		//create an employer

		Employer employer;
		
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
	
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		try {
			service.createCoopTerm(startDate, endDate, termId, student, employer);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CoopTerm> allCoopTerms = service.getAllCoopTerms();
		assertEquals(1, allCoopTerms.size());
	}
	
	/**
	* Test the inputs to the creation methods, to make sure correct input is received before
	* storage in the database
	*/
	@Test
	public void testCoopTermInputs() {
		assertEquals(0, service.getAllCoopTerms().size());
		assertEquals(0, service.getAllStudents().size());
		assertEquals(0, service.getAllEmployers().size());
		
		String[] errorMessages = { "Please enter a valid startDate", "Please enter a valid endDate",
				"Please enter a valid termId", "Please enter a valid Student", 
				"Please enter a valid Employer"};
		String error = "";
		
		//create a student 
		Student student;

		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		//create an employer

		Employer employer;
		
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
	
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		try {
			service.createCoopTerm(null, endDate, termId, student, employer);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertEquals(errorMessages[0], error);
		
		try {
			service.createCoopTerm(startDate, null, termId, student, employer);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);
		
		try {
			service.createCoopTerm(startDate, endDate, null, student, employer);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertEquals(errorMessages[2], error);
		
		try {
			service.createCoopTerm(startDate, endDate, termId, null, employer);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertEquals(errorMessages[3], error);
		
		try {
			service.createCoopTerm(startDate, endDate, termId, student, null);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertEquals(errorMessages[4], error);

		List<CoopTerm> allCoopTerms = service.getAllCoopTerms();
		assertEquals(0, allCoopTerms.size());
	}
	
	/**
	 * Tests the update of a CoopTerm object in the database
	 */
	@Test
	public void testUpdateCoopTerm() {
		//create a student 
		Student student;

		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		//create an employer

		Employer employer;
		
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
	
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		//create a coopTerm
		service.createCoopTerm(startDate, endDate, termId, student, employer);
		
		//updated parameters, create a new student 
		Student student2;

		id = 4343;
		name = "Yassine";
		fName = "Douida";
		emailAddress = "Douids@mcgill.ca";
		userName = "DOUDOU23";
		password = "baller";
		studentId = 260747634;
		program = "ecse";
		
		student2 = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(2, service.getAllStudents().size());
		Date newEndDate = new Date(10000000);
		
		try {
			service.updateCoopTerm(startDate, newEndDate, termId, student2, employer);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CoopTerm> allCoopTerms = service.getAllCoopTerms();
		assertEquals(newEndDate.toString(), allCoopTerms.get(0).getEndDate().toString());
		assertEquals(student2.getUserID(), allCoopTerms.get(0).getStudent().getUserID());
		assertEquals(student2.getEmailAddress(), allCoopTerms.get(0).getStudent().getEmailAddress());
		assertEquals(student2.getPassword(), allCoopTerms.get(0).getStudent().getPassword());
}
	/**
	 * Tests the deletion of a CoopTerm object from the database
	 */
	@Test
	public void testDeleteCoopTerm() {
		//create a student 
		Student student;

		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		//create an employer

		Employer employer;
		
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
	
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		//create a coopTerm
		service.createCoopTerm(startDate, endDate, termId, student, employer);
		
		try {
			service.deleteCoopTerm(termId);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		List<CoopTerm> allCoopTerms = service.getAllCoopTerms();
		assertEquals(0,allCoopTerms.size());
		
	}
	
	// ==========================================================================================

	// ==========================================================================================
	// Document Tests
	
	/**
	 * Test the creation and persistence of a Document object
	 */
	@Test
	public void testCreateAndReadDocument() {
		//Create document
		Document testDocument;
		assertEquals(0, service.getAllDocuments().size());
		
		// set calendar
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.FEBRUARY, 16, 9, 00, 0);
		
		// initialize variables
		DocumentName docName = DocumentName.courseEvaluation; //evaluation doc
		Integer docId = 21;
		Date dueDate = new Date(c.getTimeInMillis());
		Time dueTime = new Time(c.getTimeInMillis());
		
		c.set(2019, Calendar.FEBRUARY, 16, 10, 30, 0);
		
		Date subDate = new Date(c.getTimeInMillis());
		Time subTime = new Time(c.getTimeInMillis());
		
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		Student student;
		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		
		Employer employer;
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		
		CoopTerm coopTerm = service.createCoopTerm(startDate, endDate, termId, student, employer);

		
		try {
			testDocument = service.createDocument(docName, docId, dueDate, dueTime, subDate, subTime, coopTerm);
		} catch (Exception e) {
			// Check that no error occurred while creating and saving the document.
			System.out.println(e);
		}
		
		List<Document> allDocuments = service.getAllDocuments();
		
		assertEquals(1, allDocuments.size());
		assertEquals(docName, allDocuments.get(0).getDocName());
		assertEquals(docId, allDocuments.get(0).getDocId());
		assertEquals(dueDate.toString(), allDocuments.get(0).getDueDate().toString());
		assertEquals(dueTime.toString(), allDocuments.get(0).getDueTime().toString());
		assertEquals(subDate.toString(), allDocuments.get(0).getSubDate().toString());
		assertEquals(subTime.toString(), allDocuments.get(0).getSubTime().toString());
		assertEquals(coopTerm.getTermId(), allDocuments.get(0).getCoopTerm().getTermId());
		//testDocument = null;

		// TODO
	}
	
	/**
	 * Test the inputs to the creation methods, to make sure correct input is received before
	 * storage in the database
	 */
	@Test
	public void testCreateDocumentInputs() {
		// TODO
		assertEquals(0, service.getAllDocuments().size());
		String[] errorMessages = { "Document Name cannot be empty!", "Doc ID cannot be empty!",
				"Please enter a valid Date", "Please enter a valid Time", 
				"Please enter a valid Date", "Please enter a valid Time",
				"Please enter a valid CoopTerm"};
		String error = "";

		// Create input arguments to the create function
		Document testDocument;
		assertEquals(0, service.getAllDocuments().size());
		// set calendar
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.FEBRUARY, 16, 9, 00, 0);
		// initialize variables
		DocumentName docName = DocumentName.courseEvaluation;
		Integer docId = 21;
		Date dueDate = new Date(c.getTimeInMillis());
		Time dueTime = new Time(c.getTimeInMillis());
		c.set(2019, Calendar.FEBRUARY, 16, 10, 30, 0);
		Date subDate = new Date(c.getTimeInMillis());
		Time subTime = new Time(c.getTimeInMillis());
		
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		Student student;
		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		
		Employer employer;
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		
		CoopTerm coopTerm = service.createCoopTerm(startDate, endDate, termId, student, employer);

		// Test docName parsing
		try {
			// Null input
			testDocument = service.createDocument(null, docId, dueDate, dueTime, subDate, subTime, coopTerm);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[0], error);

		// Test docId parsing
		try {
			// Null input
			testDocument = service.createDocument(docName, null, dueDate, dueTime, subDate, subTime, coopTerm);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[1], error);
		
		// dueDate testing
		try {
			// Null input
			testDocument = service.createDocument(docName, docId, null, dueTime, subDate, subTime, coopTerm);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[2], error);

		// dueTime testing
		try {
			// Null input
			testDocument = service.createDocument(docName, docId, dueDate, null, subDate, subTime, coopTerm);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[3], error);

		// subDate testing
		try {
			// Null input
			testDocument = service.createDocument(docName, docId, dueDate, dueTime, null, subTime, coopTerm);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[4], error);

		// subTime testing
		try {
			// Null input
			testDocument = service.createDocument(docName, docId, dueDate, dueTime, subDate, null, coopTerm);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[5], error);

		// coopTerm testing
		try {
			// Null input
			testDocument = service.createDocument(docName, docId, dueDate, dueTime, subDate, subTime, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(errorMessages[6], error);

		// check no change in memory
		assertEquals(0, service.getAllDocuments().size());
	}
	
	/**
	 * Tests the update of a Document object in the database
	 */
	@Test
	public void testUpdateDocument() {
		//Create document
		Document testDocument;
		assertEquals(0, service.getAllDocuments().size());
		
		// set calendar
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.FEBRUARY, 16, 9, 00, 0);
		
		// initialize variables
		DocumentName docName = DocumentName.courseEvaluation; //evaluation doc
		Integer docId = 21;
		Date dueDate = new Date(c.getTimeInMillis());
		Time dueTime = new Time(c.getTimeInMillis());
		
		c.set(2019, Calendar.FEBRUARY, 16, 10, 30, 0);
		
		Date subDate = new Date(c.getTimeInMillis());
		Time subTime = new Time(c.getTimeInMillis());
		
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		Student student;
		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		
		Employer employer;
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		
		CoopTerm coopTerm = service.createCoopTerm(startDate, endDate, termId, student, employer);
		
		// Create new document object
		testDocument = service.createDocument(docName, docId, dueDate, dueTime, subDate, subTime, coopTerm);
		assertEquals(1,service.getAllDocuments().size());
		
		//updated parameters, create a new document 
		Document newDocument;
		
		// set calendar
		Calendar c1 = Calendar.getInstance();
		c1.set(2019, Calendar.FEBRUARY, 16, 9, 00, 0);
		// initialize variables
		DocumentName newdocName = DocumentName.courseEvaluation; //evaluation doc
		Date newsubDate = new Date(c1.getTimeInMillis());
		Time newsubTime = new Time(c1.getTimeInMillis());
		
		// UPDATE new docName, subDate, subTime
		newDocument = service.createDocument(newdocName, docId, dueDate, dueTime, newsubDate, newsubTime, coopTerm);
		assertEquals(1, service.getAllDocuments().size());
	
		try {
			service.updateDocument(newdocName, docId, dueDate, dueTime, newsubDate, newsubTime, coopTerm);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Document> allDocuments = service.getAllDocuments();
		assertEquals(newdocName, allDocuments.get(0).getDocName());
		assertEquals(newsubDate.toString(), allDocuments.get(0).getSubDate().toString());
		assertEquals(newsubTime.toString(), allDocuments.get(0).getSubTime().toString());
		// TODO
	}
	
	/**
	 * * Tests the deletion of a Document object from the database
	 */
	@Test
	public void testDeleteDocument() {
		//Create document
		Document testDocument;
		assertEquals(0, service.getAllDocuments().size());
		
		// set calendar
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.FEBRUARY, 16, 9, 00, 0);
		
		// initialize variables
		DocumentName docName = DocumentName.courseEvaluation; //evaluation doc
		Integer docId = 21;
		Date dueDate = new Date(c.getTimeInMillis());
		Time dueTime = new Time(c.getTimeInMillis());
		
		c.set(2019, Calendar.FEBRUARY, 16, 10, 30, 0);
		
		Date subDate = new Date(c.getTimeInMillis());
		Time subTime = new Time(c.getTimeInMillis());
		
		//Other parameters
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		Integer termId = 1;
		
		Student student;
		Integer id = 4553;
		String name = "Oscar";
		String fName = "Macsiotra";
		String emailAddress = "oscar@mcgill.ca";
		String userName = "Oscar89";
		String password = "qwerty";
		Integer studentId = 260747696;
		String program = "ecse";
		student = service.createStudent(id, name, fName, emailAddress, userName, password, studentId, program);
		assertEquals(1, service.getAllStudents().size());
		
		Employer employer;
		Integer emp_id = 234;
		String emp_name = "Tristan";
		String emp_fName = "Bougon";
		String emp_emailAddress = "tristan@mcgill.ca";
		String emp_userName = "oups";
		String emp_password = "trist90";
		String emp_companyName = "Industries";
		String emp_location = "Montreal";
		employer = service.createEmployer(emp_id, emp_name, emp_fName, emp_emailAddress, emp_userName, emp_password, emp_companyName, emp_location);
		assertEquals(1, service.getAllEmployers().size());
		
		CoopTerm coopTerm = service.createCoopTerm(startDate, endDate, termId, student, employer);
		
		// Create new document object
		testDocument = service.createDocument(docName, docId, dueDate, dueTime, subDate, subTime, coopTerm);
		assertEquals(1,service.getAllDocuments().size());
		
		// remove the document
		try {
			service.deleteDocument(docId);
		} catch (IllegalArgumentException e) {
			fail();
		}
	    	
	    //exception handling when you try to remove null
		try {
			service.deleteDocument(null);
		} catch (Exception e) {
			System.out.println(e); // not an actual document
		}
		
		assertEquals(0,service.getAllDocuments().size());
		// TODO
	}
	
	// ==========================================================================================
	
	@Test
	public void testViewStudentDocument() {
				
		testCreateAndReadDocument();
		assertEquals(1, service.getAllDocuments().size());
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopTerms().size());	
		DocumentName doc = DocumentName.courseEvaluation;
		DocumentName docc = DocumentName.finalReport;
		Document newDoc = new Document();
		
		int studId = service.getAllStudents().get(0).getUserID();
		int termId = service.getAllCoopTerms().get(0).getTermId();
		try {
			newDoc = service.viewStudentDocument(studId, termId, docc);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(null, newDoc.getDocName());
		
		try {
			newDoc = service.viewStudentDocument(studId, termId, doc);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(doc, newDoc.getDocName());
		
	}
	
	@Test
	public void testViewEmployerDocument() {
		
		testCreateAndReadDocument();
		assertEquals(1, service.getAllDocuments().size());
		assertEquals(1, service.getAllEmployers().size());
		assertEquals(1, service.getAllCoopTerms().size());	
		DocumentName doc = DocumentName.courseEvaluation;
		DocumentName docc = DocumentName.finalReport;
		Document newDoc = new Document();
		
		int empId = service.getAllEmployers().get(0).getUserID();
		int termId = service.getAllCoopTerms().get(0).getTermId();
		try {
			newDoc = service.viewEmployerDocument(empId, termId, docc);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(null, newDoc.getDocName());
		
		try {
			newDoc = service.viewEmployerDocument(empId, termId, doc);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(doc, newDoc.getDocName());
	}
	
	@Test
	public void testViewSudentFiles() {
		
		testCreateAndReadDocument();
		assertEquals(1, service.getAllDocuments().size());
		assertEquals(1, service.getAllEmployers().size());
		assertEquals(1, service.getAllCoopTerms().size());	
		DocumentName doc = DocumentName.courseEvaluation;
		DocumentName docc = DocumentName.finalReport;
		Set<Document> documents = Collections.emptySet();
		
		
		
		
	}
	

}