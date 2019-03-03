package ca.mcgill.ecse321.cooperator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//imports add for writting tests
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.cooperator.dao.*;
import ca.mcgill.ecse321.cooperator.dto.CoopTermDto;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.junit4.SpringRunner;
import ca.mcgill.ecse321.cooperator.controller.CooperatorController;
import ca.mcgill.ecse321.cooperator.model.*;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;

//Static imports from methods
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CooperatorApplicationTests {
	
	//Create the DAO mocks
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
	 
	@InjectMocks
	private CooperatorController controller;
	
	//Mock a student 
	private Student student;
	private static final String STUDENT_KEY = "TestStudent";
	private static final String NONEXISTING_KEY = "NotAStudent";

	//Mock an employer 
	private Employer employer;
	private static final String EMPLOYER_KEY = "TestStudent";
	//private static final String NONEXISTING_KEY = "NotAStudent";
	
		@Before
		public void setStudentMockOutput() {
		 when(studentDao.findByLastName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
		    if(invocation.getArgument(0).equals(STUDENT_KEY)) {
		      Student student = new Student();
		      student.setLastName(STUDENT_KEY);
		      return student;
		    } else {
		      return null;
		    }
		  });
		}
		
		@Before
		public void setEmployerMockOutput() {
		 when(employerDao.findByLastName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
		    if(invocation.getArgument(0).equals(EMPLOYER_KEY)) {
		      Employer employer = new Employer();
		      employer.setCompanyName(EMPLOYER_KEY);
		      return employer;
		    } else {
		      return null;
		    }
		  });
		}
		
		@Before
		public void setupMock() {
			student = mock(Student.class);
			employer = mock(Employer.class);
		}

		@Test
		public void testMockPersonCreation() {
			assertNotNull(student);
			assertNotNull(employer);
			assertEquals(STUDENT_KEY, service.getStudentByName(STUDENT_KEY).getLastName());
			assertEquals(EMPLOYER_KEY, service.getEmployerByLastName(EMPLOYER_KEY).getCompanyName());
			
			Date startDate = new Date(0);
			Date endDate = new Date(1);
			CoopTerm coopterm;
			coopterm = service.createCoopTerm(startDate, endDate, student, employer);

			assertEquals(startDate.toString(), coopterm.getStartDate().toString());

		}

		@Test
		public void testPersonQueryFound() {
		  assertEquals(STUDENT_KEY, service.getStudentByName(STUDENT_KEY).getLastName());
		  assertEquals(EMPLOYER_KEY, service.getEmployerByLastName(EMPLOYER_KEY).getCompanyName());
		}
	
	
		@Test
		public void contextLoads() {
		}

}

