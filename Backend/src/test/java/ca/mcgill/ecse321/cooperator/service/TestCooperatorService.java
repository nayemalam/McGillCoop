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
import ca.mcgill.ecse321.cooperator.dao.CoopTermRepository;
import ca.mcgill.ecse321.cooperator.dao.DocumentRepository;
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
	
	@Before
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		documentRepository.deleteAll();
		// Then we can clear the other tables
		systemUserRepository.deleteAll();
		coopTermRepository.deleteAll();
		cooperatorSystemRepository.deleteAll();
	}

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
			// Check that no error occurred
			System.out.println("SUH");
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
	
	
//	@Test
//	public void testCreateCoopTerm() {
//		assertEquals(0, service.getAllCoopTerms().size());
//		
//		Date startDate = new Date(0);
//		System.out.println(startDate);
//		Date endDate = new Date(1);
//		Integer termId = 1;
//		
//		try {
//			service.createCoopTerm(startDate, endDate, termId);
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
//	
//	@Test
//	public void testCreateCoopterm() {
//		assertEquals(0, service.getAllCoopTerms().size());
//
//		@SuppressWarnings("deprecation")
//		Date startDate = new Date(8,8,2017);
//		@SuppressWarnings("deprecation")
//		Date endDate = new Date(8,8,2018);
//
//		Integer termId = 21;
//		
//		
//
//		try {
//			service.createCoopTerm(startDate, endDate, termId);
//		} catch (IllegalArgumentException e) {
//			// Check that no error occurred
//			fail();
//		}
//
//		List<CoopTerm> allCoopTerm = service.getAllCoopTerms();
//
//		assertEquals(1, allCoopTerm.size());
//		assertEquals(termId, allCoopTerm.get(0).getTermId());
//	}
	@Test
	public void emptyMethod() {
		assertEquals(0, 0);
	}
//
//	@Test
//	public void testCreatePersonNull() {
//		assertEquals(0, service.getAllPersons().size());
//
//		String name = null;
//		String error = null;
//
//		try {
//			service.createPerson(name);
//		} catch (IllegalArgumentException e) {
//			error = e.getMessage();
//		}
//
//		// check error
//		assertEquals("Person name cannot be empty!", error);
//
//		// check no change in memory
//		assertEquals(0, service.getAllPersons().size());
//
//	}

}
