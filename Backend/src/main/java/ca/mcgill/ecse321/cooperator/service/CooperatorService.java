package ca.mcgill.ecse321.cooperator.service;

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
import ca.mcgill.ecse321.cooperator.dao.CoopAdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
import javax.activation.*;

import java.io.IOException;
import java.lang.Exception;

import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
import javax.activation.*;
import java.lang.Exception;

@Service
public class CooperatorService {

	@Autowired
	CooperatorSystemRepository cooperatorSystemRepository;
	@Autowired
	CoopTermRepository coopTermRepository;
	@Autowired
	SystemUserRepository systemUserRepository;
	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	CoopAdministratorRepository coopAdministratorRepository;
	@Autowired
	EmployerRepository employerRepository;
	@Autowired
	StudentRepository studentRepository;

	// ==========================================================================================
	// generic SystemUser CRUD transactions

	/**
	 * Generic User fetcher
	 * 
	 * @param id - UserID for the user
	 * @return Specified user, if existing
	 */
	@Transactional
	public SystemUser getUser(Integer id) {
		SystemUser user = systemUserRepository.findByuserID(id);
		return user;
	}

	/**
	 * Used to fetch all users from the database
	 * 
	 * @return List of systemUsers
	 */
	@Transactional
	public List<SystemUser> getAllUsers() {
		return toList(systemUserRepository.findAll());
	}

	/**
	 * Unused, as all types of users have this method
	 * 
	 * @deprecated
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean deleteUser(Integer id) {

		return false;
	}

	// ==========================================================================================

	// ==========================================================================================
	// Student CRUD transactions

	/**
	 * Creates a new Student object and stores it in the database
	 * 
	 * @param name         Last name of the individual
	 * @param fName        First name of the individual
	 * @param emailAddress Email Address of the user, used for login and
	 *                     notifications
	 * @param userName     userName of the user, can be used for login purposes
	 * @param password     Used for login purposes
	 * @param studentID    McGill ID of the student
	 * @param program      Student's undergraduate major program
	 * @return Newly created user
	 */
	@Transactional
	public Student createStudent(String name, String fName, String emailAddress, String userName, String password,
			Integer studentId, String program) {
		// Parse input arguments to determine if all information is present to create a
		// new student.
		if (name == null || name.trim().length() == 0 || fName == null || fName.trim().length() == 0) {
			throw new IllegalArgumentException("Person name cannot be empty!");
		}
		if (emailAddress == null || emailAddress.trim().length() == 0) {
			throw new IllegalArgumentException("Email Address cannot be empty!");
		}
		if (userName == null || userName.trim().length() == 0) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid password");
		}
		if (studentId == null) {
			throw new IllegalArgumentException("Please enter a valid McGill Student ID");
		}
		if (program == null || program.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid program");
		}
		Student user = new Student();
		user.setLastName(name);
		user.setFirstName(fName);
		user.setEmailAddress(emailAddress);
		user.setUserName(userName);
		user.setPassword(password);
		user.setStudentId(studentId);
		user.setProgram(program);
		user = studentRepository.save(user);
		return user;
	}

	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());

	}

	/**
	 * Finds and retrieves a student from the database based on the User ID number
	 * 
	 * @param id System ID number
	 * @return Requested Student.
	 */
	@Transactional
	public Student getStudent(Integer id) {
		return studentRepository.findByuserID(id);

	}
	
	//I added this method for the mock tests (Thomas) 
	/**
	 * Finds and retrieves a student from the database based on the last name
	 * 
	 * @param lastName Student last name String
	 * @return Requested Student.
	 */
	@Transactional
	public Student getStudentByName(String lastName) {
		return studentRepository.findByLastName(lastName);
	}

	/**
	 * Updates the Student information in the database based on the User ID number
	 * 
	 * @param updatedStudent Modified student object, to be stored in database/
	 * @return {@code true} if student successfully updated, {@code false} otherwise
	 */
	@Transactional
	public Boolean updateStudent(Integer id, String name, String fName, String emailAddress, String userName,
			String password, Integer studentId, String program) {

		if (studentExists(id)) {
			// Get current student record from the database
			Student currentStudent = getStudent(id);

			// Update relevant fields if they are different in the updated student
			// Update last name
			if (!currentStudent.getLastName().equals(name)) {
				currentStudent.setLastName(name);
			}
			// Update first name
			if (!currentStudent.getFirstName().equals(fName)) {
				currentStudent.setFirstName(fName);
			}
			// Update email address
			if (!currentStudent.getEmailAddress().equals(emailAddress)) {
				currentStudent.setEmailAddress(emailAddress);
			}
			// Update username
			if (!currentStudent.getUserName().equals(userName)) {
				currentStudent.setUserName(userName);
			}
			// Update password
			if (!currentStudent.getPassword().equals(password)) {
				currentStudent.setPassword(password);
			}
			// Update student ID
			if (!currentStudent.getStudentId().equals(studentId)) {
				currentStudent.setStudentId(studentId);
			}
			// Update Program
			if (!currentStudent.getProgram().equals(program)) {
				currentStudent.setProgram(program);
			}

			return true;
		}
		return false;
	}

	/**
	 * Deletes Student from database using the User ID number
	 * 
	 * @param id System ID number of the user
	 */
	@Transactional
	public Boolean deleteStudent(Integer id) {
		// The delete method throws an IllegalArgumentError if the id is null
		try {
			studentRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Verifies the existence of a student user in the database using the User ID
	 * 
	 * @param id user ID number of the user
	 * @return True if student exists, false otherwise.
	 */
	@Transactional
	public Boolean studentExists(Integer id) {
		return studentRepository.existsById(id);

	}

	/**
	 * Views the StudentFiles in the database
	 * 
	 * @param id user ID number of the student, termId of the CoopTerm
	 * @return List of Documents submitted by the Student and Employer for that
	 *         CoopTerm
	 */

	@Transactional
	public List<Document> viewStudentFiles(Integer id, Integer termId) {
		CoopTerm currentTerm = new CoopTerm();
		Set<Document> studentDocuments = Collections.emptySet();

		if (studentExists(id) && coopTermExists(termId)) {
			// Get current student record from the database
			Student currentStudent = getStudent(id);
			// Get coopTerm from Database
			Set<CoopTerm> coopterms = currentStudent.getCoopTerm();

			// Find coopterm in the Set
			Iterator<CoopTerm> iter = coopterms.iterator();

			while (iter.hasNext()) {
				CoopTerm term = iter.next();
				if (term.getTermId().equals(termId)) {
					currentTerm = term;

				}
			}
			// set of all documents in the coopterm from the student
			studentDocuments = currentTerm.getDocument();
			List<Document> list = new ArrayList<Document>(studentDocuments);
			return list;
		}

		throw new IllegalArgumentException("The Document could not be found");

	}

	/**
	 * Method used to view the files submitted by a student
	 * 
	 * @param id      - Student's UserID number
	 * @param termId  - TermID for which to view the submissions
	 * @param docname - Name of the document to view
	 * @return
	 */
	@Transactional
	public Document viewStudentDocument(Integer id, Integer termId, DocumentName docname) {
		// This business method returns a document from the list of documents of a term.
		List<Document> documents = viewStudentFiles(id, termId);

		// document that will be returned
		Document document_modif = new Document();

		// iterate through the document list of the term
		int i;
		for (i = 0; i < documents.size(); i++) {
			// stop when the document type is found
			Document doc = documents.get(i);
			if (doc.getDocName().equals(docname)) {
				document_modif = doc;
			}
		}
		return document_modif;
	}

	// ==========================================================================================

	/**
	 * Create new Cooperator System Class Not really needed but present, just in
	 * case!
	 * 
	 * @param systemId Identifier of the new system
	 */
	@Transactional
	public CooperatorSystem createCooperatorSystem(Integer systemId) {
		if (systemId == null) {
			throw new IllegalArgumentException("Please enter a valid systemId");
		}
		CooperatorSystem cooperatorSystem = new CooperatorSystem();
		cooperatorSystem.setSystemId(systemId);

		cooperatorSystemRepository.save(cooperatorSystem);
		return cooperatorSystem;
	}

	/**
	 * Finds and retrieves a CooperatorSystem from the database based on the System
	 * ID number
	 * 
	 * @param id System ID number
	 * @return Requested CooperatorSystem.
	 */
	@Transactional
	public CooperatorSystem getCooperatorSystem(Integer systemId) {
		return cooperatorSystemRepository.findBysystemId(systemId);
	}

	/**
	 * Obtain a list of all Cooperator systems. Again, not really needed, but
	 * present in case
	 */
	@Transactional
	public List<CooperatorSystem> getAllCooperatorSystems() {
		return toList(cooperatorSystemRepository.findAll());
	}

	/**
	 * Verifies if a CooperatorSystem exists in database based on the given ID
	 * 
	 * @return {@code true} if exists, {@code false} otherwise
	 */
	@Transactional
	public Boolean cooperatorSystemExists(Integer id) {
		Boolean exists = cooperatorSystemRepository.existsById(id);
		return exists;
	}

	/**
	 * Deletes the specified CooperatorSystem Object from the database
	 */
	@Transactional
	public void deleteCooperatorSystem(Integer id) {
		cooperatorSystemRepository.deleteById(id);
	}

	// ==========================================================================================
	// Co-op Admin CRUD

	/**
	 * Creates a new CoopAdministrator object and stores it in the database
	 * 
	 * @param id           System ID attributed to new user
	 * @param name         Last name of the individual
	 * @param fName        First name of the individual
	 * @param emailAddress Email Address of the user, used for login and
	 *                     notifications
	 * @param userName     userName of the user, can be used for login purposes
	 * @param password     Used for login purposes
	 * @return Newly created user
	 */
	@Transactional
	public CoopAdministrator createCoopAdministrator(String name, String fName, String emailAddress, String userName,
			String password) {
		// Parse input arguments to determine if all information is present to create a
		// new CoopAdmin.
		if (name == null || name.trim().length() == 0 || fName == null || fName.trim().length() == 0) {
			throw new IllegalArgumentException("Person name cannot be empty!");
		}
		if (emailAddress == null || emailAddress.trim().length() == 0) {
			throw new IllegalArgumentException("Email Address cannot be empty!");
		}
		if (userName == null || userName.trim().length() == 0) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid password");
		}

		CoopAdministrator user = new CoopAdministrator();
		// user.setUserID(id);
		user.setLastName(name);
		user.setFirstName(fName);
		user.setEmailAddress(emailAddress);
		user.setUserName(userName);
		user.setPassword(password);
		coopAdministratorRepository.save(user);
		return user;
	}

	
	/**
	 * Updates the CoopAdministrator information in the database based on the User
	 * ID number
	 * 
	 * @param updatedCoopAdministrator Modified coopAdministrator object, to be
	 *                                 stored in database/
	 * @return {@code true} if coopAdministrator successfully updated, {@code false}
	 *         otherwise
	 */
	@Transactional
	public Boolean updateCoopAdministrator(Integer id, String name, String fName, String emailAddress, String userName,
			String password) {
		if (coopAdministratorExists(id)) {
			// Get current student record from the database, user ID wont change between new
			// and old coopadmin
			CoopAdministrator currentCoopAdministrator = getCoopAdministrator(id);

			// Update relevant fields if they are different in the updated coopAdministrator
			// Update last name
			if (currentCoopAdministrator.getLastName() != name) {
				currentCoopAdministrator.setLastName(name);
			}
			// Update first name
			if (currentCoopAdministrator.getFirstName() != fName) {
				currentCoopAdministrator.setFirstName(fName);
			}
			// Update email address
			if (currentCoopAdministrator.getEmailAddress() != emailAddress) {
				currentCoopAdministrator.setEmailAddress(emailAddress);
			}
			// Update username
			if (currentCoopAdministrator.getUserName() != userName) {
				currentCoopAdministrator.setUserName(userName);
			}
			// Update password
			if (currentCoopAdministrator.getPassword() != password) {
				currentCoopAdministrator.setPassword(password);
			}

			return true;
		}
		return false;
	}

	/**
	 * Finds all coopAdministrators and returns them in a list
	 * 
	 * @return {@code List<CoopAdministrator>}
	 */
	@Transactional
	public List<CoopAdministrator> getAllCoopAdministrators() {
		return toList(coopAdministratorRepository.findAll());
	}

	/**
	 * Finds and retrieves a CoopAdministrator from the database based on the User
	 * ID number
	 * 
	 * @param id User ID number
	 * @return Requested CoopAdministrator.
	 */
	@Transactional
	public CoopAdministrator getCoopAdministrator(Integer id) {
		CoopAdministrator user = coopAdministratorRepository.findByuserID(id);
		return user;
	}

	/**
	 * Finds and retrieves a CoopAdministrator email from the database based on the
	 * User Email
	 * 
	 * @param emailAddress User Email Address
	 * @return Requested {@code CoopAdministrator}.
	 */
	@Transactional
	public CoopAdministrator getCoopAdministratorEmail(String emailAddress) {
		CoopAdministrator user_email = coopAdministratorRepository.findByemailAddress(emailAddress);
		return user_email;
	}

	/**
	 * Verifies the existence of a CoopAdministrator user in the database using the
	 * User ID
	 * 
	 * @param id User ID number of the user
	 * @return True if student exists, false otherwise.
	 */
	@Transactional
	public Boolean coopAdministratorExists(Integer id) {
		Boolean exists = coopAdministratorRepository.existsById(id);
		return exists;
	}

	/**
	 * Deletes CoopAdministrator from database using the User ID number
	 * 
	 * @param id User ID number of the user
	 */
	@Transactional
	public void deleteCoopAdministrator(Integer id) {
		coopAdministratorRepository.deleteById(id);
	}

	/**
	 * Deletes all coop Administrators from the database
	 */
	@Transactional
	public void deleteAllCoopAdministrators() {
		coopAdministratorRepository.deleteAll();
	}

	/**
	 * Business method from the Use Case diagram. This method allows the caller to
	 * view the statistics for a particular Coop term, based on that term's TermID
	 * 
	 * @param semesterDate - Date that falls within the semester's start and end
	 *                     date
	 */
	@Transactional
	public termStatistics getStatisticsBySemester(Date semesterDate) {
		// Get all students signed up to coopterm
		List<CoopTerm> coopterms = getAllCoopTerms();

		// We need to find the current coopterms associated to some semester
		List<CoopTerm> currentTerms = new ArrayList<>();

		for (int i = 0; i < coopterms.size(); i++) {
			CoopTerm term = coopterms.get(i);
			Date startDate = term.getStartDate();
			Date endDate = term.getEndDate();

			// If the date inputed falls between the start and end date
			// Add term to a list
			if ((semesterDate.compareTo(startDate) >= 0) && (semesterDate.compareTo(endDate) <= 0)) {
				currentTerms.add(term);
			}
		}

		// Now that we have current terms associated to the semester,
		// we can obtain a few statistics from this
		// Obtain all unique students in those terms
		List<Student> currentStudents = new ArrayList<>();
		Student stu;
		for (int i = 0; i < currentTerms.size(); i++) {
			stu = currentTerms.get(i).getStudent();
			if (!currentStudents.contains(stu)) {
				currentStudents.add(stu);
			}

		}
		// From this list, we can obtain statistics
		// Create termStatistics object
		termStatistics stats = new termStatistics();

		Integer numberAtWork = getAmountOfStudentsInCoopTerm(currentStudents);
		List<Student> numberInFirstWorkTerm = getSemesterOfStudy(currentStudents, 1);
		List<Student> numberInSecondWorkTerm = getSemesterOfStudy(currentStudents, 2);
		List<Student> numberInThirdWorkTerm = getSemesterOfStudy(currentStudents, 3);
		stats.setNumberAtWork(numberAtWork);
		stats.setFirstWorkTerm(numberInFirstWorkTerm);
		stats.setSecondWorkTerm(numberInSecondWorkTerm);
		stats.setThirdWorkTerm(numberInThirdWorkTerm);

		return stats;

	}

	/**
	 * Class used by the service class to make passing of term statistics easier.
	 * 
	 * @author Tristan Bouchard
	 *
	 */
	class termStatistics {

		// Member variables
		private Integer numberAtWork_;
		private List<Student> firstWorkTerm_;
		private List<Student> secondWorkTerm_;
		private List<Student> thirdWorkTerm_;

		// Getters
		public Integer getNumberAtWork() {
			return this.numberAtWork_;
		}

		public List<Student> getFirstWorkTerm() {
			return this.firstWorkTerm_;
		}

		public List<Student> getSecondWorkTerm() {
			return this.secondWorkTerm_;
		}

		public List<Student> getThirdWorkTerm() {
			return this.thirdWorkTerm_;
		}

		// Setters
		public void setNumberAtWork(Integer numberAtWork) {
			this.numberAtWork_ = numberAtWork;
		}

		public void setFirstWorkTerm(List<Student> stu) {
			this.firstWorkTerm_ = stu;
		}

		public void setSecondWorkTerm(List<Student> stu) {
			this.secondWorkTerm_ = stu;
		}

		public void setThirdWorkTerm(List<Student> stu) {
			this.thirdWorkTerm_ = stu;
		}
	}

	/**
	 * Statistic method used by getStatisticsBySemester
	 * 
	 * @param semesterDate - Date that falls within the semester's start and end
	 *                     date
	 */
	public Integer getAmountOfStudentsInCoopTerm(List<Student> students) {
		return students.size();
	}

	/**
	 * Method to obtain the amount of students in a current term of study. Used by
	 * getStatisticsBySemester
	 * 
	 * @param students - {@code List<Student>} That contains the student objects
	 * @param term     - Semester of study of students wanted in list
	 * @return
	 */
	public List<Student> getSemesterOfStudy(List<Student> students, Integer term) {
		List<Student> searchList = new ArrayList<>();
		Student stu;
		for (int i = 0; i < students.size(); i++) {
			stu = students.get(i);
			if (stu.getCoopTerm().size() == term) {
				searchList.add(stu);
			}
		}
		return searchList;
	}

	/**
	 * Method used to send an email reminder from the CoopAdminstrator's email
	 * address to the student's email address. Can have multiple types of
	 * notifications
	 * 
	 * @param coopAdminUserId - UserID of the CoopAdministrator
	 * @param studentUserID   - UserID of the Student
	 * @param notifType       - 1 is a pre-reminder of a submission, can be
	 *                        programmed to be whenever before an assignment. 2 is a
	 *                        late submission notification. 3 gets a custom message,
	 *                        can be a front-end feature.
	 * @return
	 * @throws MessagingException - If the email cannot be sent.
	 */
//	@Transactional
//	public Boolean sendReminder(Integer coopAdminUserId, Integer studentUserID, Integer notifType) throws MessagingException {
//		// Verify if the Student exists in database
//		if(studentRepository.existsById(studentUserID) && coopAdministratorRepository.existsById(coopAdminUserId)){
//			// Get student from database based on the UserID
//			Student student = studentRepository.findByuserID(studentUserID);
//			CoopAdministrator coopAdmin = coopAdministratorRepository.findByuserID(coopAdminUserId);
//			// Send a reminder to the email address associated with the student just
//			// extracted
//			String to = student.getEmailAddress();
//			String from = coopAdmin.getEmailAddress();
//			String host = "127.0.0.1";
//			Properties properties = System.getProperties();
//			properties.setProperty("mail.smtp.host", host);
//			Session session = Session.getInstance(properties, null);
//			
//			// Create Message
//			MimeMessage message = new MimeMessage(session);
//			// Set sender
//			message.setFrom(new InternetAddress(from));
//			// Set receiver
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			
//			// Write message
//			String[] messageSubjects = {"CoOperator - Upcoming Submission", 
//										"CoOperator - Late Sumbission"};
//			String[] messageContents = {"Hello " +student.getFirstName()+ ",\n\n" 
//					+"This is a notification from the CoOperator System to let you know that you have an upcoming submission."
//					+"Please sign in to your CoOperator Account for more information.\n",
//					"Hello " +student.getFirstName()+ ",\n\n"
//					+"This is a notification from the CoOperator System to let you know that you have a late submission."
//					+"Please sign in to your CoOperator Account for more information.\n"
//					};
//			
//			switch (notifType) {
//				// Upcoming submission date
//				case 1:
//					message.setSubject(messageSubjects[0]);
//					message.setText(messageContents[0]);
//				// Late Submission
//				case 2:
//					message.setSubject(messageSubjects[1]);
//					message.setText(messageContents[1]);
//				// Custom Email
//				case 3:
//					// Something frontend?
//					
//			}	
//			try {
//				// Send message
//				Transport.send(message);
//			} catch (MessagingException mex) {
//				// If exception occurs, return false
//				String error = mex.getMessage();
//				throw new MessagingException("Email not successfully sent");
//			}
//			// If message succesfully sent
//			return true;
//		}
//		// If student ID does not exist
//		return false;
//	}

	// ==========================================================================================

	// ==========================================================================================
	// Employer CRUD

	/**
	 * Creates a new employer object and stores it in the database
	 * 
	 * @param id           System ID attributed to new user
	 * @param name         Last name of the individual
	 * @param fName        First name of the individual
	 * @param emailAddress Email Address of the user, used for login and
	 *                     notifications
	 * @param userName     userName of the user, can be used for login purposes
	 * @param password     Used for login purposes
	 * @param companyName  Name of the Employer Company
	 * @param location     Location of the employer
	 * @return Newly created user
	 */
	@Transactional
	public Employer createEmployer(String name, String fName, String emailAddress, String userName, String password,
			String companyName, String location) {
		// Parse input arguments to determine if all information is present to create a
		// new Employer.
		if (name == null || name.trim().length() == 0 || fName == null || fName.trim().length() == 0) {
			throw new IllegalArgumentException("Person name cannot be empty!");
		}
		if (emailAddress == null || emailAddress.trim().length() == 0) {
			throw new IllegalArgumentException("Email Address cannot be empty!");
		}
		if (userName == null || userName.trim().length() == 0) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid password");
		}
		if (companyName == null || companyName.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid company name");

		}
		if (location == null || location.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid location");
		}

		Employer user = new Employer();
		// user.setUserID(id);
		user.setLastName(name);
		user.setFirstName(fName);
		user.setEmailAddress(emailAddress);
		user.setUserName(userName);
		user.setPassword(password);
		user.setCompanyName(companyName);
		user.setLocation(location);
		employerRepository.save(user);
		return user;
	}

	/**
	 * Updates the Employer information in the database based on the User ID number
	 * 
	 * @param updatedEmployer Modified employer object, to be stored in database
	 * @return {@code true} if employer successfully updated, {@code false}
	 *         otherwise
	 */
	@Transactional
	public Boolean updateEmployer(Integer id, String name, String fName, String emailAddress, String userName,
			String password, String companyName, String location) {
		if (employerExists(id)) {

			// Get current employer record from the database, user ID wont change between
			// new and old employer
			Employer currentEmployer = getEmployer(id);

			// Update relevant fields if they are different in the updated employer
			// Update last name
			if (currentEmployer.getLastName() != name) {
				currentEmployer.setLastName(name);
			}
			// Update first name
			if (currentEmployer.getFirstName() != fName) {
				currentEmployer.setFirstName(fName);
			}
			// Update email address
			if (currentEmployer.getEmailAddress() != emailAddress) {
				currentEmployer.setEmailAddress(emailAddress);
			}
			// Update username
			if (currentEmployer.getUserName() != userName) {
				currentEmployer.setUserName(userName);
			}
			// Update password
			if (currentEmployer.getPassword() != password) {
				currentEmployer.setPassword(password);
			}
			// Update location
			if (currentEmployer.getLocation() != companyName) {
				currentEmployer.setLocation(companyName);
			}
			// Update companyname
			if (currentEmployer.getCompanyName() != location) {
				currentEmployer.setCompanyName(location);
			}

			return true;
		}
		return false;
	}

	/**
	 * Obtains list of all employers in the database
	 * 
	 * @return
	 */
	@Transactional
	public List<Employer> getAllEmployers() {
		return toList(employerRepository.findAll());
	}

	/**
	 * Finds and retrieves an Employer from the database based on the User ID number
	 * 
	 * @param id user ID number
	 * @return Requested Employer.
	 */
	@Transactional
	public Employer getEmployer(Integer id) {
		Employer user = employerRepository.findByuserID(id);
		return user;
	}
	
	@Transactional
	public Employer getEmployerByLastName(String name) {
		Employer user = employerRepository.findByLastName(name);
		return user;
	}

	/**
	 * Verifies the existence of a student user in the database using the User ID
	 * 
	 * @param id User ID number of the user
	 * @return True if student exists, false otherwise.
	 */
	@Transactional
	public Boolean employerExists(Integer id) {
		Boolean exists = employerRepository.existsById(id);
		return exists;
	}

	/**
	 * Deletes Employer from database using the User ID number
	 * 
	 * @param id User ID number of the user
	 */
	@Transactional
	public void deleteEmployer(Integer id) {
		employerRepository.deleteById(id);
	}

	/**
	 * Method to delete all employers from the database
	 */
	@Transactional
	public void deleteAllEmployers() {
		employerRepository.deleteAll();

	}

	/**
	 * Views the Employer files in the database
	 * 
	 * @param id user ID number of the employer, termId of the CoopTerm
	 * @return Set of Documents submitted by the Student and Employer for that
	 *         CoopTerm
	 */
	Set<Document> documentsSet = Collections.emptySet();

	@Transactional
	public List<Document> viewEmployerFiles(Integer id, Integer termId) {
		List<Document> doc_list = new ArrayList<Document>();

		if (employerExists(id) && coopTermExists(termId)) {
			CoopTerm currentTerm = new CoopTerm();
			// Get current Employer record from the database
			Employer currentEmployer = getEmployer(id);
			// Get coopTerm from Database
			Set<CoopTerm> coopterms = currentEmployer.getCoopTerm();

			// Find coopterm in the Set
			Iterator<CoopTerm> iter = coopterms.iterator();

			while (iter.hasNext()) {
				CoopTerm term = iter.next();
				if (term.getTermId().equals(termId)) {
					currentTerm = term;
				}
			}
			// set of all documents in the coopterm from the Employer
			documentsSet = currentTerm.getDocument();
			doc_list = new ArrayList<Document>(documentsSet);
		}
		return doc_list;
	}

	/**
	 * Method used to view a specific Employer Document
	 * 
	 * @param id      - UserID associated to the employer
	 * @param termId  - TermID of the term for which documents are wanted
	 * @param docname = Name of the specific document
	 * @return Single specified document, if it exists
	 */
	@Transactional
	public Document viewEmployerDocument(Integer id, Integer termId, DocumentName docname) {
		// This business method returns a document from the list of documents of a term.
		List<Document> documents = viewEmployerFiles(id, termId);

		// document that will be returned
		Document document_modif = new Document();

		// iterate through the document list of the term
		Iterator<Document> iter = documents.iterator();
		while (iter.hasNext()) {
			Document doc = iter.next();
			// stop when the document type is found
			if (doc.getDocName().equals(docname)) {
				document_modif = doc;
			}
		}
		return document_modif;
	}

	// ==========================================================================================
	// Document CRUD

	/**
	 * Method used to create new document in the database
	 * 
	 * @param docName  - Name of the document, of type {@code DocumentName}
	 * @param dueDate  - Date at which the document is due
	 * @param dueTime  - Time on the date before which the document must be
	 *                 submitted
	 * @param subDate  - Date of submission of the document
	 * @param subTime  - Time of the document submission
	 * @param coopTerm - CoopTerm associated to the document
	 * @return
	 */
	@Transactional
	public Document createDocument(DocumentName docName, Date dueDate, Time dueTime, Date subDate, Time subTime,
			CoopTerm coopTerm) {
		if (docName == null) {
			throw new IllegalArgumentException("Document Name cannot be empty!");
		}
		if (dueDate == null) {
			throw new IllegalArgumentException("Please enter a valid Date");
		}
		if (dueTime == null) {
			throw new IllegalArgumentException("Please enter a valid Time");
		}
		if (subDate == null) {
			throw new IllegalArgumentException("Please enter a valid Date");
		}
		if (subTime == null) {
			throw new IllegalArgumentException("Please enter a valid Time");
		}
		if (coopTerm == null) {
			throw new IllegalArgumentException("Please enter a valid CoopTerm");
		}

		Document document = new Document();
		// document.setDocId(docId);
		document.setDocName(docName);
		document.setDueDate(dueDate);
		document.setDueTime(dueTime);
		document.setSubDate(subDate);
		document.setSubTime(subTime);
		document.setCoopTerm(coopTerm);
		documentRepository.save(document);

		return document;
	}

	/**
	 * Obtain all documents from the database
	 * 
	 * @return {@code List<Document>}
	 */
	@Transactional
	public List<Document> getAllDocuments() {
		return toList(documentRepository.findAll());
	}

	/**
	 * Finds and retrieves a Document from the database based on the doc ID number
	 * 
	 * @param id document ID number
	 * @return Requested Document.
	 */
	@Transactional
	public Document getDocument(Integer docId) {
		Document document = documentRepository.findBydocId(docId);
		return document;
	}

	/**
	 * Verifies the existence of a document user in the database using the Doc ID
	 * 
	 * @param id Doc ID number of the document
	 * @return True if document exists, false otherwise.
	 */
	@Transactional
	public Boolean documentExists(Integer docId) {
		Boolean exists = documentRepository.existsById(docId);
		return exists;
	}

	/**
	 * Updates the Document information in the database based on the Doc ID number
	 * 
	 * @param updatedDocument Modified document object, to be stored in database/
	 * @return {@code true} if document successfully updated, {@code false}
	 *         otherwise
	 */
	@Transactional
	public Boolean updateDocument(DocumentName docName, Integer docId, Date dueDate, Time dueTime, Date subDate,
			Time subTime, CoopTerm coopTerm) {
		if (documentExists(docId)) {
			// Get current document record from the database, doc ID wont change between
			// new and old document
			Document currentDocument = getDocument(docId);

			// Update relevant fields if they are different in the updated document
			// Update coopterm
			if (currentDocument.getCoopTerm() != coopTerm) {
				currentDocument.setCoopTerm(coopTerm);
			}
			// Update dueDate
			if (currentDocument.getDueDate() != dueDate) {
				currentDocument.setDueDate(dueDate);
			}
			// Update dueTime
			if (currentDocument.getDueTime() != dueTime) {
				currentDocument.setDueTime(dueTime);
			}
			// Update subDate
			if (currentDocument.getSubDate() != subDate) {
				currentDocument.setSubDate(subDate);
			}
			// Update subTime
			if (currentDocument.getSubTime() != subTime) {
				currentDocument.setSubTime(subTime);
			}

			// If modifications have been carried out on the temporary object, update the
			// database
			return true;
		}
		return false;
	}

	/**
	 * Deletes Document from database using the doc ID number
	 * 
	 * @param docId Doc ID number of the document
	 */
	@Transactional
	public void deleteDocument(Integer docId) {
		documentRepository.deleteById(docId);
	}

	/**
	 * Deletes all documents from the database
	 */
	@Transactional
	public void deleteAllDocuments() {
		documentRepository.deleteAll();
		;
	}

	// ==========================================================================================
	// CoopTerm CRUD

	/**
	 * Create new CoopTerm. Must have associated student and employer created a priori
	 * @param startDate - Date of start of the Coop Term
	 * @param endDate - Date of end of the CoopTerm
	 * @param student - Student to associate to the CoopTerm
	 * @param employer - Employer of the student for that CoopTerm
	 * @return Created CoopTerm object
	 */
	@Transactional
	public CoopTerm createCoopTerm(Date startDate, Date endDate, Student student, Employer employer) {

		if (startDate == null) {
			throw new IllegalArgumentException("Please enter a valid startDate");
		}
		if (endDate == null) {
			throw new IllegalArgumentException("Please enter a valid endDate");
		}

		if (student == null) {
			throw new IllegalArgumentException("Please enter a valid Student");
		}
		if (employer == null) {
			throw new IllegalArgumentException("Please enter a valid Employer");
		}

		CoopTerm coopTerm = new CoopTerm();
		coopTerm.setStartDate(startDate);
		coopTerm.setEndDate(endDate);
		coopTerm.setStudent(student);
		coopTerm.setEmployer(employer);
		coopTermRepository.save(coopTerm);
		return coopTerm;
	}

	/**
	 * Get CoopTerm by specified TermId from the database
	 * @param termId - TermID of the CoopTerm
	 * @return
	 */
	@Transactional
	public CoopTerm getCoopTerm(Integer termId) {
		CoopTerm coopTerm = coopTermRepository.findBytermId(termId);
		return coopTerm;
	}
	
	

	/**
	 * Obtain all CoopTerm objects from the database
	 * @return
	 */
	@Transactional
	public List<CoopTerm> getAllCoopTerms() {
		return toList(coopTermRepository.findAll());
	}

	/**
	 * Verifies the existence of a coopterm in the database using the term ID
	 * 
	 * @param id term ID number of the coopTerm
	 * @return True if coopterm exists, false otherwise.
	 */
	@Transactional
	public Boolean coopTermExists(Integer termId) {
		Boolean exists = coopTermRepository.existsById(termId);
		return exists;
	}

	/**
	 * Deletes Coopterm from database using the term ID number
	 * 
	 * @param termId term ID number of the coopterm
	 */
	@Transactional
	public void deleteCoopTerm(Integer termId) {
		coopTermRepository.deleteById(termId);
	}

	@Transactional
	public void deleteAllCoopTerms() {
		coopTermRepository.deleteAll();
	}

	/**
	 * Updates the Coopterm information in the database based on the term ID number
	 * 
	 * @param updatedCoopTerm Modified coopTerm object, to be stored in database/
	 * @return {@code true} if coopterm successfully updated, {@code false}
	 *         otherwise
	 */
	@Transactional
	public Boolean updateCoopTerm(Date startDate, Date endDate, Integer termId, Student student, Employer employer) {
		if (coopTermExists(termId)) {
			// Get current coopterm record from the database, term ID wont change between
			// new and old coopterm
			CoopTerm currentCoopTerm = getCoopTerm(termId);

			// Update relevant fields if they are different in the updated document
			// Update coopTerm
			if (!currentCoopTerm.getStudent().equals(student)) {
				currentCoopTerm.setStudent(student);
			}
			// Update employer
			if (!currentCoopTerm.getEmployer().equals(employer)) {
				currentCoopTerm.setEmployer(employer);
			}
			// Update startDate
			if (currentCoopTerm.getStartDate().compareTo(startDate) != 0) {
				currentCoopTerm.setStartDate(startDate);
			}
			// Update endDate
			if (currentCoopTerm.getEndDate().compareTo(endDate) != 0) {
				currentCoopTerm.setEndDate(endDate);
			}

			return true;
		}
		return false;
	}

	// ==========================================================================================

	/**
	 * Returns objects in an iterable list.
	 * 
	 * @param iterable - Must be a list of iterable objects
	 * @return List of objects in iterable set.
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	// ==========================================================================================
	// User Login

	/**
	 * Login method, used for the login of the user
	 * 
	 * @param inputEmail    - Email address of the user, normally the McGill email
	 *                      address used for MyCourses
	 * @param inputPassword - Password chosen by the user
	 * @return Boolean depending on success of login
	 */
	@Transactional
	public boolean loginSuccess(String inputEmail, String inputPassword) {
		// Find the email in the database, check password is valid
		
		// input check
		if (inputEmail == null) {
			throw new IllegalArgumentException("Please enter a valid email.");
		}
		if (inputPassword == null) {
			throw new IllegalArgumentException("Please enter a password.");
		}

		CoopAdministrator admin = coopAdministratorRepository.findByemailAddress(inputEmail);

		if (!inputEmail.equals(admin.getEmailAddress()) || !inputPassword.equals(admin.getPassword())) {
			return false;
		}

//		if(inputEmail != admin.getEmailAddress()) {
//			System.out.println("This is not a registered email.");
//		}
		return true;

	}

	/**
	 * Method used by The getIncompleteStatements, which verifies the placements of
	 * a specified user
	 * 
	 * @param userId   - The user ID for which to verify placement
	 * @param CoopTerm - Term for which to verify the documents
	 * @return
	 */
	@Transactional
	public boolean isIncomplete(Integer userId, Integer coopTermId) {

		//find the objects in the database
		CoopTerm currentTerm = coopTermRepository.findBytermId(coopTermId);
		Student student = studentRepository.findByuserID(userId);
		
//		// Find CoopTerm in the Set
//		Set<CoopTerm> coopterms = student.getCoopTerm();

		Document document;
		// Find all documents in the CoopTerm of the student
		Set<Document> documents = currentTerm.getDocument();
		Iterator<Document> iterDocs = documents.iterator();

		// make sure it's within current date
		java.sql.Date currDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		// while there are documents
		if (coopTermExists(coopTermId)) {
			while (iterDocs.hasNext()) {
				document = iterDocs.next();
			
				if (document.getSubDate().before(currDate) || document.getSubDate().equals(currDate)
						|| document.getSubDate().after(currDate)){
					if (document.getSubDate() == null || document.getSubTime() == null) {
						return true;
					}
					// check if submissionDate exceeds dueDate
					if (document.getSubDate().after(document.getDueDate())) {
						return true;
					}
					if (document.getSubDate().toString().equals(document.getDueDate().toString()) && document.getSubTime().after(document.getDueTime())){
						return true;
					}
					
				}
			}	
		}
		return false;
	}

	/**
	 * Method used to obtain incomplete placements for the current term
	 * 
	 * @return List of students with incomplete statements
	 */
	@Transactional
	public List<Student> getIncompletePlacements() {

		// get all coop terms
		List<CoopTerm> coopterms = getAllCoopTerms();
		List<CoopTerm> currentTermList = new ArrayList<>();
		Date date;

		// get current date
		date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		// ensures that the date is within the current term
		CoopTerm currentTerm;
		for (int i = 0; i < coopterms.size(); i++) {
			currentTerm = coopterms.get(i);
			if (date.before(coopterms.get(i).getEndDate())) {
				currentTermList.add(currentTerm); // return as a list of current terms
			}
		}
		
		// students
		List<Student> incompleteStudents = new ArrayList<>();

		for (int i = 0; i < currentTermList.size(); i++) {
			/*
			 * Get student id that is associated with incomplete term Return current term at
			 * specified index and its associated id
			 */
			if (isIncomplete(currentTermList.get(i).getStudent().getUserID(), currentTermList.get(i).getTermId())) {
				// populate the incomplete students list
				incompleteStudents.add(currentTermList.get(i).getStudent());
			}
		}

		return incompleteStudents;
	}
}