package ca.mcgill.ecse321.cooperator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//imports add for writting tests
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.cooperator.dao.*;
import ca.mcgill.ecse321.cooperator.dto.CoopTermDto;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.junit4.SpringRunner;
import ca.mcgill.ecse321.cooperator.controller.CooperatorController;
import ca.mcgill.ecse321.cooperator.model.*;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;

import static org.hamcrest.CoreMatchers.is;
//Static imports from methods
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;
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

	// Mock a student
	private Student student;
	private static final String STUDENT_KEY = "TestStudent";
	private static final String fNameStudent = "TestFirstNameStudent";
	private static final String emailAddressStudent = "testStudent@mcgill.ca";
	private static final String userNameStudent = "testStudentUserName";
	private static final String passwordStudent = "testStudentPassword";
	private static final Integer studentIdStudent = 260747696;
	private static final String programStudent = "testProgramStudent";
	private static final String NONEXISTING_KEY = "NotAStudentStudent";

	// Mock an employer
	private Employer employer;
	private static final String EMPLOYER_KEY = "TestEmployer";
	private static final String fNameEmployer = "TestFirstNameEmployer";
	private static final String emailAddressEmployer = "testEmployer@mcgill.ca";
	private static final String userNameEmployer = "testUserNameEmployer";
	private static final String passwordEmployer = "testPasswordEmployer";
	private static final String companyNameEmployer = "testCompanyName";
	private static final String locationEmployer = "testLocationEmployer";
	// private static final String NONEXISTING_KEY = "NotAStudent";

	// Mock a CoopTerm
	private CoopTerm coopTerm;
	private static final Integer COOPTERM_KEY = 1234;

	// Mock a Document
	private Document document;
	private static final String docNameDocument = "TestDocumentName";
	private static final Integer DOCUMENT_KEY = 1;
	private static final DocumentName docName = DocumentName.courseEvaluation;

	@Before
	public void setStudentMockOutput() {
		when(studentDao.findByLastName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STUDENT_KEY)) {
				Student student = new Student();
				student.setLastName(STUDENT_KEY);
				student.setFirstName(fNameStudent);
				student.setEmailAddress(emailAddressStudent);
				student.setUserName(userNameStudent);
				student.setPassword(passwordStudent);
				student.setStudentId(studentIdStudent);
				student.setProgram(programStudent);
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
				Employer employer = new Employer();
				employer.setLastName(EMPLOYER_KEY);
				employer.setFirstName(fNameEmployer);
				employer.setEmailAddress(emailAddressEmployer);
				employer.setUserName(userNameEmployer);
				employer.setPassword(passwordEmployer);
				employer.setCompanyName(companyNameEmployer);
				employer.setLocation(locationEmployer);
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
				CoopAdministrator coopAdministrator = new CoopAdministrator();
				coopAdministrator.setLastName(ADMIN_KEY);
				coopAdministrator.setFirstName(fNameAdmin);
				coopAdministrator.setEmailAddress(emailAddressAdmin);
				coopAdministrator.setUserName(userNameAdmin);
				coopAdministrator.setPassword(passwordAdmin);
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
			} else {
				return null;
			}
		});
	}

	@Before
	public void setDocumentMockOutput() {
		when(documentDao.findBydocId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(DOCUMENT_KEY)) {
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
	}

	@Test
	public void testEmployerQueryFound() {
		assertEquals(EMPLOYER_KEY, service.getEmployerByLastName(EMPLOYER_KEY).getLastName());
	}

	@Test
	public void testCoopAdministratorQueryFound() {
		assertEquals(ADMIN_KEY, service.getCoopAdministratorEmail(emailAddressAdmin).getLastName());
	}

	@Test
	public void testCoopTermQueryFound() {
		assertEquals(COOPTERM_KEY, service.getCoopTerm(COOPTERM_KEY).getTermId());
	}

	@Test
	public void testDocumentQueryFound() {
		assertEquals(DOCUMENT_KEY, service.getDocument(DOCUMENT_KEY).getDocId());
	}

	@Test
	public void testMockCoopTermCreation() {
		assertNotNull(student);
		assertNotNull(employer);
		assertEquals(STUDENT_KEY, service.getStudentByName(STUDENT_KEY).getLastName());
		assertEquals(companyNameEmployer, service.getEmployerByLastName(EMPLOYER_KEY).getCompanyName());
		Date startDate = new Date(0);
		Date endDate = new Date(1);
		CoopTerm coopterm;
		coopterm = service.createCoopTerm(startDate, endDate, student, employer);
		assertEquals(startDate.toString(), coopterm.getStartDate().toString());

	}

//		@Test
//		public void testMockStudentStatistics() {
//			Calendar c = Calendar.getInstance();
//			c.set(2019, Calendar.MARCH, 20, 9, 00, 0);
//			Date semDate = new Date(c.getTimeInMillis());
//			assertNotNull(student);
//			assertNotNull(employer);
//			when(service.getStatisticsBySemester(semDate).getNumberAtWork().intValue()).thenReturn(1);
//		}

	@Test
	public void contextLoads() {

	}

}
