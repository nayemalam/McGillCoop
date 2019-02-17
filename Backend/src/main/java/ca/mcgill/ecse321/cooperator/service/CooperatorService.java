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
import java.util.List;

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
	public Student createStudent(Integer id, String name, String fName, String emailAddress, String userName,
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
		if (program == null) {
			throw new IllegalArgumentException("Please enter a valid program");
		}
		Student user = new Student();
		user.setUserID(id);
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
	 * Finds and retrieves a student from the database based on the System ID number
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
	public Boolean updateStudent(Student updatedStudent) {
		if (studentExists(updatedStudent.getUserID())) {
			// Boolean variable to monitor if a database save is required
			Boolean modified = false;
			// Get current student record from the database
			Student currentStudent = getStudent(updatedStudent.getUserID());

			// Create a temporary student identical to the current student
			Student tempStudent = currentStudent;

			// Update relevant fields if they are different in the updated student
			// Update last name
			if (currentStudent.getLastName() != updatedStudent.getLastName()) {
				tempStudent.setLastName(updatedStudent.getLastName());
				modified = true;
			}
			// Update first name
			if (currentStudent.getFirstName() != updatedStudent.getFirstName()) {
				tempStudent.setFirstName(updatedStudent.getFirstName());
				modified = true;
			}
			// Update email address
			if (currentStudent.getEmailAddress() != updatedStudent.getEmailAddress()) {
				tempStudent.setEmailAddress(updatedStudent.getEmailAddress());
				modified = true;
			}
			// Update username
			if (currentStudent.getUserName() != updatedStudent.getUserName()) {
				tempStudent.setUserName(updatedStudent.getUserName());
				modified = true;
			}
			// Update password
			if (currentStudent.getPassword() != updatedStudent.getPassword()) {
				tempStudent.setPassword(updatedStudent.getPassword());
				modified = true;
			}
			// Update student ID
			if (currentStudent.getStudentId() != updatedStudent.getStudentId()) {
				tempStudent.setStudentId(updatedStudent.getStudentId());
				modified = true;
			}
			// Update Program
			if (currentStudent.getProgram() != updatedStudent.getProgram()) {
				tempStudent.setProgram(updatedStudent.getProgram());
				modified = true;
			}
			// Update Coop Terms
			if (currentStudent.getCoopTerm() != updatedStudent.getCoopTerm()) {
				tempStudent.setCoopTerm(updatedStudent.getCoopTerm());
				modified = true;
			}

			// If modifications have been carried out on the temporary object, update the
			// database
			if (modified) {
				deleteStudent(currentStudent.getUserID());
				studentRepository.save(tempStudent);
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

	// ==========================================================================================
	// Co-op Admin CRUD

	@Transactional
	public CoopAdministrator createCoopAdministrator(Integer id, String name, String fName, String emailAddress,
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

		CoopAdministrator user = new CoopAdministrator();
		user.setUserID(id);
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
	public Boolean updateCoopAdministrator(CoopAdministrator updatedCoopAdministrator) {
		if (coopAdministratorExists(updatedCoopAdministrator.getUserID())) {
			// Boolean variable to monitor if a database save is required
			Boolean modified = false;
			// Get current student record from the database, user ID wont change between new
			// and old coopadmin
			CoopAdministrator currentCoopAdministrator = getCoopAdministrator(updatedCoopAdministrator.getUserID());

			// Create a temporary coopadmin identical to the coopadmin student
			CoopAdministrator tempCoopAdministrator = currentCoopAdministrator;

			// Update relevant fields if they are different in the updated coopAdministrator
			// Update last name
			if (currentCoopAdministrator.getLastName() != updatedCoopAdministrator.getLastName()) {
				tempCoopAdministrator.setLastName(updatedCoopAdministrator.getLastName());
				modified = true;
			}
			// Update first name
			if (currentCoopAdministrator.getFirstName() != updatedCoopAdministrator.getFirstName()) {
				tempCoopAdministrator.setFirstName(updatedCoopAdministrator.getFirstName());
				modified = true;
			}
			// Update email address
			if (currentCoopAdministrator.getEmailAddress() != updatedCoopAdministrator.getEmailAddress()) {
				tempCoopAdministrator.setEmailAddress(updatedCoopAdministrator.getEmailAddress());
				modified = true;
			}
			// Update username
			if (currentCoopAdministrator.getUserName() != updatedCoopAdministrator.getUserName()) {
				tempCoopAdministrator.setUserName(updatedCoopAdministrator.getUserName());
				modified = true;
			}
			// Update password
			if (currentCoopAdministrator.getPassword() != updatedCoopAdministrator.getPassword()) {
				tempCoopAdministrator.setPassword(updatedCoopAdministrator.getPassword());
				modified = true;
			}

			// If modifications have been carried out on the temporary object, update the
			// database
			if (modified) {
				deleteCoopAdministrator(currentCoopAdministrator.getUserID());
				coopAdministratorRepository.save(tempCoopAdministrator);
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
	public Employer createEmployer(Integer id, String name, String fName, String emailAddress, String userName,
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
		if (companyName == null || companyName.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid company name");

		}
		if (location == null || location.trim().length() == 0) {
			throw new IllegalArgumentException("Please enter a valid location");

		}

		Employer user = new Employer();
		user.setUserID(id);
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
	public Boolean updateEmployer(Employer updatedEmployer) {
		if (employerExists(updatedEmployer.getUserID())) {
			// Boolean variable to monitor if a database save is required
			Boolean modified = false;
			// Get current employer record from the database, user ID wont change between
			// new and old employer
			Employer currentEmployer = getEmployer(updatedEmployer.getUserID());

			// Create a temporary employer identical to the current employer
			Employer tempEmployer = currentEmployer;

			// Update relevant fields if they are different in the updated employer
			// Update last name
			if (currentEmployer.getLastName() != updatedEmployer.getLastName()) {
				tempEmployer.setLastName(updatedEmployer.getLastName());
				modified = true;
			}
			// Update first name
			if (currentEmployer.getFirstName() != updatedEmployer.getFirstName()) {
				tempEmployer.setFirstName(updatedEmployer.getFirstName());
				modified = true;
			}
			// Update email address
			if (currentEmployer.getEmailAddress() != updatedEmployer.getEmailAddress()) {
				tempEmployer.setEmailAddress(updatedEmployer.getEmailAddress());
				modified = true;
			}
			// Update username
			if (currentEmployer.getUserName() != updatedEmployer.getUserName()) {
				tempEmployer.setUserName(updatedEmployer.getUserName());
				modified = true;
			}
			// Update password
			if (currentEmployer.getPassword() != updatedEmployer.getPassword()) {
				tempEmployer.setPassword(updatedEmployer.getPassword());
				modified = true;
			}
			// Update location
			if (currentEmployer.getLocation() != updatedEmployer.getLocation()) {
				tempEmployer.setLocation(updatedEmployer.getLocation());
				modified = true;
			}
			// Update companyname
			if (currentEmployer.getCompanyName() != updatedEmployer.getCompanyName()) {
				tempEmployer.setCompanyName(updatedEmployer.getCompanyName());
				modified = true;
			}

			// If modifications have been carried out on the temporary object, update the
			// database
			if (modified) {
				deleteEmployer(currentEmployer.getUserID());
				employerRepository.save(tempEmployer);
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
		;
	}

	// ==========================================================================================
	// Document CRUD

	@Transactional
	public Document document(DocumentName docName, Integer docId) {
		Document document = new Document();
		document.setDocId(docId);
		document.setDocName(docName);

		documentRepository.save(document);

		return document;
	}

	@Transactional
	public List<Document> getAllDocuments() {
		return toList(documentRepository.findAll());
	}

	@Transactional
	public CoopTerm createCoopTerm(Date startDate, Date endDate, Integer termId) {
		CoopTerm coopTerm = new CoopTerm();
		coopTerm.setStartDate(startDate);
		coopTerm.setEndDate(endDate);
		coopTerm.setTermId(termId);
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

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
