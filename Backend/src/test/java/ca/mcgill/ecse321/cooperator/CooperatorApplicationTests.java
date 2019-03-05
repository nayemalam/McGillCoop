package ca.mcgill.ecse321.cooperator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//imports add for writting tests
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ca.mcgill.ecse321.cooperator.dao.*;

import org.mockito.invocation.InvocationOnMock;
import ca.mcgill.ecse321.cooperator.controller.CooperatorController;
import ca.mcgill.ecse321.cooperator.model.*;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;

//Static imports from methods
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CooperatorApplicationTests {

	// Create the DAO mocks
	@Mock
	private SystemUserRepository systemUserDao;

	@Mock
	private StudentRepository studentDao;

	@Mock
	private EmployerRepository employerDao;

	@Mock
	private CoopAdministratorRepository coopAdministratorDao;

	@Mock
	private CooperatorSystemRepository cooperatorSystemDao;

	@Mock
	private CoopTermRepository coopTermDao;

	@Mock
	private DocumentRepository documentDao;

	@InjectMocks
	private CooperatorService service;

	@Mock
	private CooperatorService serviceMock;

	@InjectMocks
	private CooperatorController controller;

	// Mock a Cooperator Administrator
	private CoopAdministrator coopAdministrator;
	private static final String ADMIN_KEY = "TestAdmin";
	private static final String fNameAdmin = "TestFirstNameAdmin";
	private static final String emailAddressAdmin = "testAdmin@mcgill.ca";
	private static final String userNameAdmin = "testAdminName";
	private static final String passwordAdmin = "testAdminPassword";
	private static final Integer USERID_ADMIN = 1;

	// Mock a student
	private Student student;
	private static final String STUDENT_KEY = "TestStudent";
	private static final String fNameStudent = "TestFirstNameStudent";
	private static final String emailAddressStudent = "testStudent@mcgill.ca";
	private static final String userNameStudent = "testStudentUserName";
	private static final String passwordStudent = "testStudentPassword";
	private static final Integer studentIdStudent = 260747696;
	private static final String programStudent = "testProgramStudent";
	private static final Integer USERID_STUDENT = 2;

	// Mock an employer
	private Employer employer;
	private static final String EMPLOYER_KEY = "TestEmployer";
	private static final String fNameEmployer = "TestFirstNameEmployer";
	private static final String emailAddressEmployer = "testEmployer@mcgill.ca";
	private static final String userNameEmployer = "testUserNameEmployer";
	private static final String passwordEmployer = "testPasswordEmployer";
	private static final String companyNameEmployer = "testCompanyName";
	private static final String locationEmployer = "testLocationEmployer";
	private static final Integer USERID_EMPLOYER = 3;

	// Mock a CoopTerm
	private CoopTerm coopTerm;
	private static final Integer COOPTERM_KEY = 4;

	// Mock a Document
	private Document document;
	private static final String docNameDocument = "TestDocumentName";
	private static final Integer DOCUMENT_KEY = 5;
	private static final DocumentName docName = DocumentName.courseEvaluation;

	
	
	public Student createMockStudent() {
		Student student = new Student();
		student.setLastName(STUDENT_KEY);
		student.setFirstName(fNameStudent);
		student.setEmailAddress(emailAddressStudent);
		student.setUserName(userNameStudent);
		student.setPassword(passwordStudent);
		student.setStudentId(studentIdStudent);
		student.setProgram(programStudent);
		student.setUserID(USERID_STUDENT);
		return student;
	}
	
	public Employer createMockEmployer() {
		Employer employer = new Employer();
		employer.setLastName(EMPLOYER_KEY);
		employer.setFirstName(fNameEmployer);
		employer.setEmailAddress(emailAddressEmployer);
		employer.setUserName(userNameEmployer);
		employer.setPassword(passwordEmployer);
		employer.setCompanyName(companyNameEmployer);
		employer.setLocation(locationEmployer);
		employer.setUserID(USERID_EMPLOYER);
		return employer;
	}
	
	public CoopAdministrator createMockAdmin() {
		CoopAdministrator coopAdministrator = new CoopAdministrator();
		coopAdministrator.setLastName(ADMIN_KEY);
		coopAdministrator.setFirstName(fNameAdmin);
		coopAdministrator.setEmailAddress(emailAddressAdmin);
		coopAdministrator.setUserName(userNameAdmin);
		coopAdministrator.setPassword(passwordAdmin);
		coopAdministrator.setUserID(USERID_ADMIN);
		return coopAdministrator;
	}
	
	public CoopTerm createMockTerm(Student student, Employer employer) {
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.FEBRUARY, 16, 9, 00, 0);
		Date startDate = new Date(c.getTimeInMillis());
		c.set(2019, Calendar.APRIL, 16, 9, 00, 0);
		Date endDate = new Date(c.getTimeInMillis());
		CoopTerm coopTerm = new CoopTerm();
		coopTerm.setStartDate(startDate);
		coopTerm.setEndDate(endDate);
		coopTerm.setStudent(student);
		coopTerm.setEmployer(employer);
		coopTerm.setTermId(COOPTERM_KEY);
		return coopTerm;
		
	}
	
	public Document createMockDocument(CoopTerm coopTerm) {
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.FEBRUARY, 16, 9, 00, 0);
		Date startDate = new Date(c.getTimeInMillis());
		c.set(2019, Calendar.APRIL, 16, 9, 00, 0);
		Date endDate = new Date(c.getTimeInMillis());
		Document document = new Document();
		document.setDueDate(startDate);
		document.setSubDate(endDate);
		document.setCoopTerm(coopTerm);
		document.setDocName(docName);
		document.setDocId(DOCUMENT_KEY);
		return document;
	
	}
	
	@Before
	public void setStudentMockOutput() {
		when(studentDao.findByLastName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STUDENT_KEY)) {
				student = createMockStudent();
				return student;
			} else {
				return null;
			}
		});
		
		when(studentDao.findByuserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USERID_STUDENT)) {
				student = createMockStudent();
				return student;
			} else {
				return null;
			}
		});
	
	}

	@Before
	public void setEmployerMockOutput() {
		when(employerDao.findByLastName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(EMPLOYER_KEY)) {
				employer = createMockEmployer();
				return employer;
			} else {
				return null;
			}
		});
		
		when(employerDao.findByuserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USERID_EMPLOYER)) {
				employer = createMockEmployer();
				return employer;
			} else {
				return null;
			}
		});
	}

	@Before
	public void setCoopAdminMockOutput() {
		when(coopAdministratorDao.findByemailAddress(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(emailAddressAdmin)) {
				coopAdministrator = createMockAdmin();
				return coopAdministrator;
			} else {
				return null;
			}
		});
		
		when(coopAdministratorDao.findByuserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USERID_ADMIN)) {
				coopAdministrator = createMockAdmin();
				return coopAdministrator;
			} else {
				return null;
			}
		});
	}

	@Before
	public void setCoopTermMockOutput() {
		when(coopTermDao.findBytermId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(COOPTERM_KEY)) {
				student = createMockStudent(); 
				employer = createMockEmployer();
				coopTerm = createMockTerm(student, employer);
				return coopTerm;
			} else {
				return null;
			}
		});
		
		
	}

	@Before
	public void setDocumentMockOutput() {
		when(documentDao.findBydocId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(DOCUMENT_KEY)) {
				student = createMockStudent(); 
				employer = createMockEmployer();
				coopTerm = createMockTerm(student, employer);
				document = createMockDocument(coopTerm);
				return document;
			} else {
				return null;
			}
		});
	}

	@Before
	public void setupMock() {
		student = mock(Student.class);
		employer = mock(Employer.class);
		coopAdministrator = mock(CoopAdministrator.class);
		coopTerm = mock(CoopTerm.class);
		document = mock(Document.class);

	}

	@Test
	public void testStudentQueryFound() {
		assertEquals(STUDENT_KEY, service.getStudentByName(STUDENT_KEY).getLastName());
		assertEquals(STUDENT_KEY, service.getStudent(USERID_STUDENT).getLastName());
		
		
	}

	@Test
	public void testEmployerQueryFound() {
		assertEquals(EMPLOYER_KEY, service.getEmployerByLastName(EMPLOYER_KEY).getLastName());
		assertEquals(USERID_EMPLOYER, service.getEmployer(USERID_EMPLOYER).getUserID());
	}

	@Test
	public void testCoopAdministratorQueryFound() {
		assertEquals(ADMIN_KEY, service.getCoopAdministratorEmail(emailAddressAdmin).getLastName());
		assertEquals(USERID_ADMIN, service.getCoopAdministrator(USERID_ADMIN).getUserID());
	}

	@Test
	public void testCoopTermQueryFound() {
		//make sure the coopterm is mocked
		assertEquals(COOPTERM_KEY, service.getCoopTerm(COOPTERM_KEY).getTermId());
		//make sure that the coopTerm has a valid employer
		assertEquals(EMPLOYER_KEY, service.getCoopTerm(COOPTERM_KEY).getEmployer().getLastName());
		//make sure that the coopTerm has a valid student 
		assertEquals(STUDENT_KEY, service.getCoopTerm(COOPTERM_KEY).getStudent().getLastName());
		
	}

	@Test
	public void testDocumentQueryFound() {
		assertEquals(DOCUMENT_KEY, service.getDocument(DOCUMENT_KEY).getDocId());
	}


	
	/**
	 * Test the login business method
	 */
	@Test 
	public void testLogin() {
		
		//assert that the login was indeed successful
		assertEquals(true, service.loginSuccess(emailAddressAdmin,passwordAdmin));
	}

	/**
	 * Test the view student files business method
	 */
//	@Test 
//	public void testViewStudentFiles() {
//		List<Document> docList = new ArrayList();
//		
//		docList = service.viewStudentFiles(USERID_STUDENT, COOPTERM_KEY);
//		
//		
//	}
//	
	@Test
	public void testMockPersonCreation() {
		assertNotNull(student);
		assertNotNull(employer);
		assertNotNull(coopAdministrator);
		assertNotNull(coopTerm);
		assertNotNull(document);
	}

	@Test
	public void contextLoads() {

	}

}
