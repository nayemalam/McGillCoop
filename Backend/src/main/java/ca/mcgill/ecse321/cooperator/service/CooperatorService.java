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
//import javax.mail.MessagingException;

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
	 * @param id - UserID of the user in database
	 * @return {@code False}, no code actually executed
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
	 * @param name         - Last name of the individual
	 * @param fName        - First name of the individual
	 * @param emailAddress - Email Address of the user, used for login and
	 *                     notifications
	 * @param userName     - userName of the user, can be used for login purposes
	 * @param password     - Used for login purposes
	 * @param studentId    - McGill ID of the student
	 * @param program      - Student's undergraduate major program
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
		user.setCoopTerm(null);
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

	// I added this method for the mock tests (Thomas)
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
	 * Updates the Student information in the database based on the User ID number.
	 * If the parameter is to remain unchanged, enter the previous student's
	 * information
	 * 
	 * @param id           - UserID of the Student. Not modified
	 * @param name         - Last name of the student
	 * @param fName        - First Name of the student
	 * @param emailAddress - Email address of the student
	 * @param userName     - User Name of the student
	 * @param password     - Password associated to the student, for login purposes
	 * @param studentId    - McGill Student ID of the Student
	 * @param program      - Major Undergraduate program of study
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
	 * 
	 * 
	 * @param id System ID number of the user
	 */
	/**
	 * Deletes Student from database using the User ID number
	 * 
	 * @param id - UserID of the student
	 * @return {@code true} if properly deleted, {@code false} otherwise
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
	
	@Transactional
	public void deleteAllStudents() {
		studentRepository.deleteAll();
	}

	/**
	 * Verifies the existence of a student user in the database using the User ID
	 * 
	 * @param id - UserID number of the Student
	 * @return {@code true} if student exists, {@code false} otherwise.
	 */
	@Transactional
	public Boolean studentExists(Integer id) {
		return studentRepository.existsById(id);

	}
	
	/**
	 * Method to see if student exists in database by studentId
	 * @param studentId - McGill Student ID number of student
	 * @return {@code true} if exists, {@code false} otherwise
	 */
	@Transactional
	public Boolean studentExistsByStudentId(Integer studentId) {
		List<Student> students = studentRepository.findByStudentId(studentId);
		if(students.size()!= 0) {
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * Views the StudentFiles in the database
	 * 
	 * @param id     - userID number of the student
	 * @param termId - termId of the CoopTerm
	 * @return A list of student Documents
	 */
	@Transactional
	public List<Document> viewStudentFiles(Integer id, Integer termId) {
		CoopTerm currentTerm = new CoopTerm();
		Set<Document> studentDocuments = Collections.emptySet();

		// if (studentExists(id) && coopTermExists(termId)) {

		if (getStudent(id) != null && getCoopTerm(termId) != null) {
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
	 * @return Single requested document
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
	 * case.
	 * 
	 * @param systemId Identifier of the new system
	 * @return Newly created Cooperator system
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
	 * @param systemId - System ID number
	 * @return Requested CooperatorSystem.
	 */
	@Transactional
	public CooperatorSystem getCooperatorSystem(Integer systemId) {
		return cooperatorSystemRepository.findBysystemId(systemId);
	}

	/**
	 * Obtain a list of all Cooperator systems. Again, not really needed, but
	 * present in case
	 * 
	 * @return List of all CooperatorSystems in the database
	 */
	@Transactional
	public List<CooperatorSystem> getAllCooperatorSystems() {
		return toList(cooperatorSystemRepository.findAll());
	}

	/**
	 * Verifies if a CooperatorSystem exists in database based on the given ID
	 * 
	 * @param id - System ID of the CooperatorSystem
	 * @return {@code true} if exists, {@code false} otherwise
	 */
	@Transactional
	public Boolean cooperatorSystemExists(Integer id) {
		Boolean exists = cooperatorSystemRepository.existsById(id);
		return exists;
	}

	/**
	 * Deletes the specified CooperatorSystem Object from the database
	 * 
	 * @param id - System ID of the Cooperator System in the database
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
	 * @param name         - Last name of the individual
	 * @param fName        - First name of the individual
	 * @param emailAddress - Email Address of the user, used for login and
	 *                     notifications
	 * @param userName     - userName of the user, can be used for login purposes
	 * @param password     - Used for login purposes
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
	 * 
	 * 
	 * @param updatedCoopAdministrator Modified coopAdministrator object, to be
	 *                                 stored in database/
	 * @return {@code true} if coopAdministrator successfully updated, {@code false}
	 *         otherwise
	 */
	/**
	 * Updates the CoopAdministrator information in the database based on updated
	 * information
	 * 
	 * @param id           - UserID of the coopAdministrator object
	 * @param name         - Last name of the Coop Administrator
	 * @param fName        - First name of Coop Administrator
	 * @param emailAddress - Email address of the Coop Administrator
	 * @param userName     - UserName of the Coop Administrator, used for login
	 *                     purposes
	 * @param password     - Password of the Coop Administrator, for login
	 * @return {@code true} if correctly modified {@code false} is not
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
	 * This business method finds a student in the database and obtains his
	 * associated coopterms, as well as the completeness of those coopterms.
	 * 
	 * @param id - userID of the student, as used by the backend
	 * @return coopterms of the student
	 */

	@Transactional
	public List<TermCompleteness> trackCoopStudent(Integer id) {

		// check if the student user exists in the database
		if (studentExists(id)) {
			// Find and retrieve a student from the database based on userId
			Student student = getStudent(id);
			// Get Coopterms of the student
			//Set<CoopTerm> coopTerms = student.getCoopTerm();
			List<CoopTerm> allCoopTerms = getAllCoopTerms();
			List<CoopTerm> suhList = new ArrayList<CoopTerm>();
			for(int i = 0 ; i<allCoopTerms.size(); i++) {
				CoopTerm coopo = allCoopTerms.get(i);
				if(coopo.getStudent().getUserID().equals(id)) {
					suhList.add(coopo);
				}
			}
			// Create list of terms complete
			List<TermCompleteness> termsComplete = new ArrayList<TermCompleteness>();

			// Iterate over the terms
			for (int i =0; i< suhList.size(); i++) {
				Set<Document> docs = suhList.get(i).getDocument();
				// Base completeness of these terms on the completeness of the documents
				TermCompleteness termComp = new TermCompleteness();
				for (Document doc : docs) {
					
					DocumentName name = doc.getDocName();
					Boolean isComplete = false;

					if (name == DocumentName.valueOf("finalReport")) {
						// Verify if document submitted
						if (doc.getSubDate() != null) {
							termComp.setFinalReportCompleteness(true);
						} else {
							termComp.setFinalReportCompleteness(false);
						}
					} else if (name == DocumentName.valueOf("taskDescription")) {
						if (doc.getSubDate() != null) {
							termComp.setTaskDescriptionCompleteness(true);
						} else {
							termComp.setTaskDescriptionCompleteness(false);
						}
					} else if (name == DocumentName.valueOf("courseEvaluation")) {
						if (doc.getSubDate() != null) {
							isComplete = true;
							termComp.setCourseEvaluationCompleteness(true);
						} else {
							termComp.setCourseEvaluationCompleteness(false);
						}
					}
				}
				termsComplete.add(termComp);
			}
			return termsComplete;
			

		} else {
			return null;
		}
	}

//	/*
//	 * Class used by the service class to make passing of student statistics easier.
//	 * This class uses the termCompleteness class as its members
//	 * 
//	 * @author Tristan Bouchard
//	 */
//	public class StudentStatistics {
//		// Member classes
//		private TermCompleteness term1;
//		private TermCompleteness term2;
//		private TermCompleteness term3;
//
//		// Constructors
//		StudentStatistics(TermCompleteness _term1) {
//			this.term1 = _term1;
//			this.term2 = null;
//			this.term3 = null;
//		}
//
//		StudentStatistics(TermCompleteness _term1, TermCompleteness _term2) {
//			this.term1 = _term1;
//			this.term2 = _term2;
//			this.term3 = null;
//		}
//
//		StudentStatistics(TermCompleteness _term1, TermCompleteness _term2, TermCompleteness _term3) {
//			this.term1 = _term1;
//			this.term2 = _term2;
//			this.term3 = _term3;
//		}
//
//		// Getters
//		public TermCompleteness getTermOne() {
//			return this.term1;
//		}
//
//		public TermCompleteness getTermTwo() {
//			return this.term2;
//		}
//
//		public TermCompleteness getTermThree() {
//			return this.term3;
//		}
//		// This class does not need setters, should set using the constructors
//	}

	/**
	 * Class used to determine which document in a coopterm has been completed.
	 * 
	 * @author Tristan Bouchard
	 *
	 */
	public class TermCompleteness {
		// Boolean variables describing if document is completed or not at the current
		// moment
		private Boolean taskDescription;
		private Boolean finalReport;
		private Boolean courseEvaluation;

		// Constructor
		public TermCompleteness() {
			this.taskDescription = false;
			this.finalReport = false;
			this.courseEvaluation = false;
		}

		public TermCompleteness(Boolean _employementContract, Boolean _taskDescription, Boolean _finalReport,
				Boolean _courseEvaluation) {
			this.taskDescription = _taskDescription;
			this.finalReport = _finalReport;
			this.courseEvaluation = _courseEvaluation;
		}

		// Getters

		public Boolean getTaskDescriptionCompleteness() {
			return this.taskDescription;
		}

		public Boolean getFinalReportCompleteness() {
			return this.finalReport;
		}

		public Boolean getCourseEvaluationCompleteness() {
			return this.courseEvaluation;
		}

		// Setters

		public void setTaskDescriptionCompleteness(Boolean isComplete) {
			this.taskDescription = isComplete;
		}

		public void setFinalReportCompleteness(Boolean isComplete) {
			this.finalReport = isComplete;
		}

		public void setCourseEvaluationCompleteness(Boolean isComplete) {
			this.courseEvaluation = isComplete;
		}
	}

//	/**
//	 * This business method find student in Database based on userID returns the
//	 * specific coopterm of the student
//	 * 
//	 * @param userId user ID of the student, coopTermId ID of the coopTerm
//	 * @return coopterm found based on userId and coopTermId
//	 */
//
//	@Transactional
//	public CoopTerm trackCoopStudentBySemester(Integer userId, Integer coopTermId) {
//		// This business method return s specific coopterm
//
//		// check if the student user exists in the database
//		if (studentExists(userId)) {
//			// Find and retrieve a student from the database based on userId
//			Student student = getStudent(userId);
//		} else {
//			return null;
//		}
//
//		CoopTerm coopTerm = new CoopTerm();
//
//		if (coopTermExists(coopTermId)) {
//			coopTerm = student.getCoopTerm(coopTermId);
//		} else {
//			return null;
//		}
//
//		// see the progress of document submission(what has been submitted)
//		List<Document> documents = coopTerm.getAllDocument();
//
//		return coopTerm;
//
//	}

	/**
	 * Business method from the Use Case diagram. This method allows the caller to
	 * view the statistics for a particular Coop term, based on that term's TermID
	 * 
	 * @param semesterDate - Date that falls within the semester's start and end
	 *                     date
	 * @return {@code TermStatistics} Object
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
	public class termStatistics {

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
	 * 
	 * @param students - List of students
	 * @return Integer size of list
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
	 * @return {@code List<Student>}
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

	
	// ==========================================================================================

	// ==========================================================================================
	// Employer CRUD

	/**
	 * Creates a new employer object and stores it in the database
	 * 
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
	 * @return
	 */
	/**
	 * 
	 * @param id           - UserId of the Employer
	 * @param name         - Last name of the Employer
	 * @param fName        - First name of the Employer
	 * @param emailAddress - Email address of the Employer
	 * @param userName     - userName of the Employer
	 * @param password     - Password of the Employer
	 * @param companyName  - Name of the company of the employer
	 * @param location     - Location of the employer company
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
	 * @return {@code List<Employer>}
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

	Set<Document> documentsSet = Collections.emptySet();

	/**
	 * Views the Employer files in the database
	 * 
	 * @param id     - user ID number of the employer
	 * @param termId - termId of the CoopTerm
	 * @return Set of Documents submitted by the Student and Employer for that
	 *         CoopTerm
	 */
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
	 * @return Newly created document
	 */
	@Transactional
	public Document createDocument(DocumentName docName, Date dueDate, Time dueTime, Date subDate, Time subTime,
			CoopTerm coopTerm, String externalDocId) {
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
		document.setExternalDocId(externalDocId);
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
	 * @param docId - document ID number
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
	 * @param docId - Doc ID number of the document
	 * @return {@code true} if document exists, {@code false} otherwise.
	 */
	@Transactional
	public Boolean documentExists(Integer docId) {
		Boolean exists = documentRepository.existsById(docId);
		return exists;
	}

	/**
	 * 
	 * 
	 * @param updatedDocument Modified document object, to be stored in database/
	 * @return
	 */
	/**
	 * Updates the Document information in the database base on the passed
	 * information
	 * 
	 * @param docName - documentName according to the enumeration
	 * @param docId   - docId of the document
	 * @param dueDate - Deadline date
	 * @param dueTime - Deadline time
	 * @param subDate - Student submission date
	 * @param subTime - Student submission time
	 * @return {@code true} if document successfully updated, {@code false}
	 *         otherwise
	 */
	@Transactional
	public Boolean updateDocument(DocumentName docName, Integer docId, Date dueDate, Time dueTime, Date subDate,
			Time subTime) {

		Document currentDocument = getDocument(docId);
		if (documentExists(docId)) {
			// Get current document record from the database, doc ID wont change between
			// new and old document

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
	 * Create new CoopTerm. Must have associated student and employer created a
	 * priori
	 * 
	 * @param startDate - Date of start of the Coop Term
	 * @param endDate   - Date of end of the CoopTerm
	 * @param student   - Student to associate to the CoopTerm
	 * @param employer  - Employer of the student for that CoopTerm
	 * @return Created CoopTerm object
	 */
	@SuppressWarnings("deprecation")
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
		coopTerm.setEmployer(employer);
		coopTerm.setStudent(student);
		@SuppressWarnings("deprecation")
		Integer month = startDate.getMonth();
		switch (month) {
			case 0:
				coopTerm.setSemester("Winter " + startDate.toString().substring(0,4));
				break;
			case 1:
				coopTerm.setSemester("Winter " + startDate.toString().substring(0,4));
				break;
			case 2:
				coopTerm.setSemester("Winter " + startDate.toString().substring(0,4));
				break;
			case 3:
				coopTerm.setSemester("Winter " + startDate.toString().substring(0,4));
				break;
			case 4:
				coopTerm.setSemester("Summer " + startDate.toString().substring(0,4));
				break;
			case 5:
				coopTerm.setSemester("Summer " + startDate.toString().substring(0,4));
				break;
			case 6:
				coopTerm.setSemester("Summer " + startDate.toString().substring(0,4));
				break;
			case 7:
				coopTerm.setSemester("Summer " + startDate.toString().substring(0,4));
				break;
			case 8:
				coopTerm.setSemester("Fall " + startDate.toString().substring(0,4));
				break;
			case 9:
				coopTerm.setSemester("Fall " + startDate.toString().substring(0,4));
				break;
			case 10:
				coopTerm.setSemester("Fall " + startDate.toString().substring(0,4));
				break;
			case 11:
				coopTerm.setSemester("Fall " + startDate.toString().substring(0,4));
				break;
		}
			
		
		coopTermRepository.save(coopTerm);
		return coopTerm;
	}
	
	
	/**
	 * Create new CoopTerm. Must have associated student and employer created a
	 * priori
	 * 
	 * @param startDate - Date of start of the Coop Term
	 * @param endDate   - Date of end of the CoopTerm
	 * @param student   - Student to associate to the CoopTerm
	 * @param employer  - Employer of the student for that CoopTerm
	 * @return Created CoopTerm object
	 */
	@Transactional
	public CoopTerm createCoopTerm(Date startDate, Date endDate, Student student, Employer employer, String semester) {

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
		coopTerm.setEmployer(employer);
		coopTerm.setStudent(student);
		coopTerm.setSemester(semester);
		
		coopTermRepository.save(coopTerm);
		return coopTerm;
	}

	/**
	 * Get CoopTerm by specified TermId from the database
	 * 
	 * @param termId - TermID of the CoopTerm
	 * @return {@code CoopTerm} Object
	 */
	@Transactional
	public CoopTerm getCoopTerm(Integer termId) {
		CoopTerm coopTerm = coopTermRepository.findBytermId(termId);
		return coopTerm;
	}

	/**
	 * Get a list of CoopTerm by specified User ID from the database
	 * 
	 * @param userId - UserId of the system user
	 * @return {@code list<CoopTerm>} 
	 */
	@Transactional
	public List<CoopTerm> getCoopTermByUserId(Integer userId) {
		List<CoopTerm> coopTermList = new ArrayList<CoopTerm>();
		List<CoopTerm> allCoopTermList = new ArrayList<CoopTerm>();
		allCoopTermList =toList(coopTermRepository.findAll());
		for (CoopTerm coopTerm : allCoopTermList) {
			if ((coopTerm.getStudent().getUserID().equals(userId))||(coopTerm.getEmployer().getUserID().equals(userId))) {
				coopTermList.add(coopTerm);
			}
		}
		return coopTermList;
	}
	
	/**
	 * Obtain all CoopTerm objects from the database
	 * 
	 * @return {@code List<CoopTerm>}
	 */
	@Transactional
	public List<CoopTerm> getAllCoopTerms() {
		return toList(coopTermRepository.findAll());
	}

	/**
	 * Verifies the existence of a coopterm in the database using the term ID
	 * 
	 * @param termId - term ID number of the coopTerm
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
	 * Updates the Coopterm information in the database based on the new information
	 * 
	 * @param startDate - New start date of the semester
	 * @param endDate   - New end date of the semester
	 * @param termId    - TermID of the associated CoopAdmin
	 * @param student   - Associated student
	 * @param employer  - Employer associated with the coopterm
	 * @return {@code true} id properly modified, {@code false} otherwise
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
	
		// input check
		if (inputEmail == null) {
			return false;
		}
		if (inputPassword == null) {
			return false;
		}
		
		// Find the email in the database, check password is valid and check if admin exists
		CoopAdministrator admin = coopAdministratorRepository.findByemailAddress(inputEmail);
		
		if(admin !=null) {
		
			if (inputEmail.equals(admin.getEmailAddress()) && inputPassword.equals(admin.getPassword())) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Method used by The getIncompleteStatements, which verifies the placements of
	 * a specified user
	 * 
	 * @param userId     - The user ID for which to verify placement
	 * @param coopTermId - Term for which to verify the documents
	 * @return {@code true} if student has incomplete statuments, {@code false}
	 *         otherwise
	 */
	@Transactional
	public boolean isIncomplete(Integer userId, Integer coopTermId) {

		// find the objects in the database
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
						|| document.getSubDate().after(currDate)) {
					if (document.getSubDate() == null || document.getSubTime() == null) {
						return true;
					}
					// check if submissionDate exceeds dueDate
					if (document.getSubDate().after(document.getDueDate())) {
						return true;
					}
					if (document.getSubDate().toString().equals(document.getDueDate().toString())
							&& document.getSubTime().after(document.getDueTime())) {
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