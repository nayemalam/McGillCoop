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

	@Transactional
	public SystemUser getUser(Integer id) {
		SystemUser user = systemUserRepository.findByuserID(id);
		return user;
	}

	@Transactional
	public List<SystemUser> getAllUsers() {
		return toList(systemUserRepository.findAll());
	}

	@Transactional
	public boolean deleteUser(Integer id) {
		// TODO
		return false;
	}

	// @Transactional
	// public boolean updateUser(Integer id) {
	// SystemUser user = systemUserRepository.findByuserID(id);
	// //TODO
	// return false;
	// }

	// ==========================================================================================

	// ==========================================================================================
	// Student CRUD transactions

	@Transactional
	public Student createStudent(String name, String fName, String emailAddress, String userName,
			String password, Integer studentId, String program) {
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
//		if (id == null) {
//			throw new IllegalArgumentException("Please enter a valid User ID");
//		}
		if (program == null || program.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid program");
		}
		Student user = new Student();
//		user.setUserID(id);
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
			// Boolean variable to monitor if a database save is required
			Boolean modified = false;
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
	 * @return Set of Documents submitted by the Student and Employer for that CoopTerm
	 */
	
	@Transactional
	public Set<Document> viewStudentFiles(Integer id, Integer termId) {

		Set<Document> documents = Collections.emptySet();		
		if (studentExists(id) && coopTermExists(termId)) {

			CoopTerm currentTerm = new CoopTerm();
			// Get current student record from the database
			Student currentStudent = getStudent(id);
			//Get coopTerm from Database
			Set<CoopTerm> coopterms = currentStudent.getCoopTerm();

			//Find coopterm in the Set
			Iterator<CoopTerm> iter = coopterms.iterator();

			while(iter.hasNext()) {
				if(iter.next().getTermId().equals(termId)) {
					currentTerm = iter.next();

				}			
			}

			//set of all documents in the coopterm from the student
			documents = currentTerm.getDocument();


		}
		return documents;
	}

	// ==========================================================================================

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

	@Transactional
	public List<CooperatorSystem> getAllCooperatorSystems() {
		return toList(cooperatorSystemRepository.findAll());
	}
	
//	@Transactional
//	public Boolean updateCooperatorSystem(Integer systemId) {
//		
//		if(cooperatorSystemExists(systemId)) {
//			
//			CooperatorSystem coopSystem = getCooperatorSystem(systemId);
//			
//			if(!coopSystem.getSystemId().equals(systemId)) {
//				
//				coopSystem.setSystemId(systemId);
//			}
//			
//		return true;
//	}
//		
//		
//		return false;
//	}

	@Transactional
	public Boolean cooperatorSystemExists(Integer id) {
		Boolean exists = cooperatorSystemRepository.existsById(id);
		return exists;
	}
	
	@Transactional
	public void deleteCooperatorSystem(Integer id) {
		cooperatorSystemRepository.deleteById(id);
	}
	
	
	// ==========================================================================================
	// Co-op Admin CRUD

	@Transactional
	public CoopAdministrator createCoopAdministrator(String name, String fName, String emailAddress,
			String userName, String password) {
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
//		if (id == null) {
//			throw new IllegalArgumentException("Please enter a valid User ID");
//		}

		CoopAdministrator user = new CoopAdministrator();
		//user.setUserID(id);
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
	public Boolean updateCoopAdministrator(Integer id, String name, String fName, String emailAddress,
			String userName, String password) {
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
	 * Finds and retrieves a CoopAdministrator email from the database based on the User
	 * Email
	 * 
	 * @param emailAddress User Email Address
	 * @return Requested CoopAdministrator.
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

	@Transactional
	public void deleteAllCoopAdministrators() {
		coopAdministratorRepository.deleteAll();
		;
	}

	// ==========================================================================================
	
	// ==========================================================================================
	// Employer CRUD

	@Transactional
	public Employer createEmployer(String name, String fName, String emailAddress, String userName,
			String password, String companyName, String location) {
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
//		if (id == null) {
//			throw new IllegalArgumentException("Please enter a valid User ID");
//		}

		Employer user = new Employer();
		//user.setUserID(id);
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
	 * @param updatedEmployer Modified employer object, to be stored in database/
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

	@Transactional
	public void deleteAllEmployers() {
		employerRepository.deleteAll();
		
	}
	/**
	 * Views the StudentFiles in the database
	 * 
	 * @param id user ID number of the employer, termId of the CoopTerm
	 * @return Set of Documents submitted by the Student and Employer for that CoopTerm
	 */
	
	@Transactional
	public Set<Document> viewEmployerFiles(Integer id, Integer termId) {

		Set<Document> documents = Collections.emptySet();		
		if (employerExists(id) && coopTermExists(termId)) {

			CoopTerm currentTerm = new CoopTerm();
			// Get current Employer record from the database
			Employer currentEmployer = getEmployer(id);
			//Get coopTerm from Database
			Set<CoopTerm> coopterms = currentEmployer.getCoopTerm();

			//Find coopterm in the Set
			Iterator<CoopTerm> iter = coopterms.iterator();

			while(iter.hasNext()) {
				if(iter.next().getTermId().equals(termId)) {
					currentTerm = iter.next();

				}			
			}

			//set of all documents in the coopterm from the Employer
			documents = currentTerm.getDocument();


		}
		return documents;
	}


	// ==========================================================================================
	// Document CRUD

	@Transactional
	public Document createDocument(DocumentName docName, Date dueDate, Time dueTime, Date subDate, Time subTime, CoopTerm coopTerm) {
		
		if (docName == null) {
			throw new IllegalArgumentException("Document Name cannot be empty!");
		}
//		if (docId == null) {
//			throw new IllegalArgumentException("Doc ID cannot be empty!");
//		}
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
	//	document.setDocId(docId);
		document.setDocName(docName);
		document.setDueDate(dueDate);
		document.setDueTime(dueTime);
		document.setSubDate(subDate);
		document.setSubTime(subTime);
		document.setCoopTerm(coopTerm);
		documentRepository.save(document);

		return document;
	}
	

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
	public Boolean updateDocument(DocumentName docName, Integer docId, Date dueDate, Time dueTime, Date subDate, Time subTime, CoopTerm coopTerm) {
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
	
	@Transactional
	public void deleteAllDocuments() {
		documentRepository.deleteAll();
		;
	}
	
	
	// ==========================================================================================
	// CoopTerm CRUD

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
		//coopTerm.setTermId(termId);
		coopTerm.setStudent(student);
		coopTerm.setEmployer(employer);
		coopTermRepository.save(coopTerm);
		return coopTerm;
	}

	@Transactional
	public CoopTerm getCoopTerm(Integer termId) {
		CoopTerm coopTerm = coopTermRepository.findBytermId(termId);
		return coopTerm;
	}

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

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	// ==========================================================================================
	// User Login
	
	@Transactional
	public void login(String inputEmail, String inputPassword){
		//Find the email in the database, check password is valid

		// input check
		if (inputEmail == null) {
			throw new IllegalArgumentException("Please enter a valid email.");
		}
		if (inputPassword == null) {
			throw new IllegalArgumentException("Please enter a password.");
		}
		
		CoopAdministrator admin = new CoopAdministrator();
		
		// find email in database
		try {
			admin = getCoopAdministratorEmail(inputEmail);
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect email.");
		}
		
		if(inputPassword != admin.getPassword()) {
			throw new IllegalArgumentException("Incorrect password.");
		}
		else {
			System.out.println("You have succesfully logged in.");
		}
//		if(inputEmail != admin.getEmailAddress()) {
//			System.out.println("This is not a registered email.");
//		}
		
	}
	
	
	@Transactional
	public boolean isIncomplete (Integer userId, Integer CoopTerm){

		CoopTerm currentTerm = new CoopTerm();	
		Student student = studentRepository.findByuserID(userId);
//		Student student = getStudent(userId);
		Document document = new Document();
		// Find CoopTerm in the Set
		Set<CoopTerm> coopterms = student.getCoopTerm();
		
		// Find all documents in the CoopTerm of the student
		Set<Document> documents = currentTerm.getDocument();
		Iterator<Document> iterDocs = documents.iterator();
		
		// make sure it's within current date
		java.sql.Date currDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		// while there are documents
		if(coopTermExists(CoopTerm)) {
			if(document.getSubDate().before(currDate) || document.getSubDate().equals(currDate) || document.getSubDate().after(currDate)) {
				while(iterDocs.hasNext()) {
					document = iterDocs.next();
					if(document.getSubDate() == null) {
						return true;
					}
					// check if submissionDate exceeds dueDate
					if(document.getSubDate().after(document.getDueDate())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	

	@Transactional
	public List<Student> getIncompletePlacements() {

		// get all coop terms
		List<CoopTerm> coopterms = getAllCoopTerms();
		CoopTerm currentTerm;		
		List<CoopTerm> currentTermList = Collections.emptyList();
		Date date;
		
		// get current date
		date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		// ensures that the date is within the current term
		if(date.before(coopterms.get(0).getEndDate())) {
			for(int i=0; i<coopterms.size(); i++) {
				currentTerm = coopterms.get(i); 
				currentTermList.add(currentTerm); // return as a list of current terms
			}
			
		} 
		
		// students
		List<Student> incompleteStudents = Collections.emptyList();
		
		for(int i =0; i<currentTermList.size();i++) {
			
			/* Get student id that is associated with incomplete term
			 * Return current term at specified index and its associated id
			*/
			if(isIncomplete(currentTermList.get(i).getStudent().getUserID(), currentTermList.get(i).getTermId())) {
				// populate the incomplete students list
				incompleteStudents.add(currentTermList.get(i).getStudent()); 
			}
		}
		
		return incompleteStudents;
	}
}