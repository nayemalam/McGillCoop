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
	
	
	//==========================================================================================
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
	public boolean deleteUser(Integer userId){
		// TODO
		return false;
	}

	@Transactional
	public boolean updateUser(Integer id) {
		SystemUser user = systemUserRepository.findByuserID(id);
		//TODO
		return false;
	}

	//==========================================================================================

	//==========================================================================================
	// Student CRUD transactions
	
	@Transactional
	public Student createStudent(Integer id, String name, String fName, String emailAddress, String userName, String password, Integer studentId, String program) {
		// Parse input arguments to determine if all information is present to create a new student.
		if (name == null || name.trim().length() == 0 || fName ==null || fName.trim().length() == 0) {
			throw new IllegalArgumentException("Person name cannot be empty!");
		}
		if (emailAddress == null || emailAddress.trim().length() ==0){
			throw new IllegalArgumentException("Email Address cannot be empty!");
		}
		if (userName == null || userName.trim().length() ==0){
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		if (password == null || password.trim().length() ==0){
			throw new IllegalArgumentException("Please enter a valid password");
		}
		if (studentId == null){
			throw new IllegalArgumentException("Please enter a valid McGill Student ID");
		}
		Student user = new Student();
		user.setLastName(name);
		user.setFirstName(fName);
		user.setEmailAddress(emailAddress);
		user.setUserName(userName);
		user.setPassword(password);
		user.setStudentId(studentId);
		studentRepository.save(user);
		return user;
	}

	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	/**
	 * Finds and retrieves a student from the database based on the System ID number
	 * @param id System ID number
	 * @return Requested Student.
	 */
	@Transactional
	public Student getStudent(Integer id) {
		return studentRepository.findByuserID(id);
	}
	/**
	 * Updates the Student information in the database based on the System ID number
	 * @param updatedStudent Modified student object, to be stored in database/
	 * @return {@code true} if student successfully updated, {@code false} otherwise
	 */
	@Transactional
	public Boolean updateStudent(Student updatedStudent){
		if(studentExists(updatedStudent.getUserID())){
			// Boolean variable to monitor if a database save is required
			Boolean modified = false;
			// Get current student record from the database
			Student currentStudent = getStudent(updatedStudent.getUserID());

			// Create a temporary student identical to the current student
			Student tempStudent = currentStudent;

			// Update relevant fields if they are different in the updated student
			// Update last name
			if(currentStudent.getLastName() != updatedStudent.getLastName()){
				tempStudent.setLastName(updatedStudent.getLastName());
				modified = true;
			}
			// Update first name
			if(currentStudent.getFirstName() != updatedStudent.getFirstName()){
				tempStudent.setFirstName(updatedStudent.getFirstName());
				modified = true;
			}
			// Update email address
			if(currentStudent.getEmailAddress() != updatedStudent.getEmailAddress()){
				tempStudent.setEmailAddress(updatedStudent.getEmailAddress());
				modified = true;
			}
			// Update username
			if(currentStudent.getUserName() != updatedStudent.getUserName()){
				tempStudent.setUserName(updatedStudent.getUserName());
				modified = true;
			}
			// Update password
			if(currentStudent.getPassword() != updatedStudent.getPassword()){
				tempStudent.setPassword(updatedStudent.getPassword());
				modified = true;
			}
			// Update student ID
			if(currentStudent.getStudentId() != updatedStudent.getStudentId()){
				tempStudent.setStudentId(updatedStudent.getStudentId());
				modified = true;
			}
			// Update Program
			if(currentStudent.getProgram() != updatedStudent.getProgram()){
				tempStudent.setProgram(updatedStudent.getProgram());
				modified = true;
			}
			// Update Coop Terms
			if(currentStudent.getCoopTerm() != updatedStudent.getCoopTerm()){
				tempStudent.setCoopTerm(updatedStudent.getCoopTerm());
				modified = true;
			}

			// If modifications have been carried out on the temporary object, update the database
			if(modified){
				deleteStudent(currentStudent.getUserID());
				studentRepository.save(tempStudent);
			}
			return true;
		}
		return false;
	}

	/**
	 * Deletes Student from database using the System ID number
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
	 * Verifies the existence of a student user in the database using the System ID
	 * @param id System ID number of the user
	 * @return True if student exists, false otherwise.
	 */
	@Transactional
	public Boolean studentExists(Integer id){
		return studentRepository.existsById(id);
	}

	//==========================================================================================


	@Transactional
	public CooperatorSystem createCooperatorSystem(Integer systemId) {
		CooperatorSystem cooperatorSystem  = new CooperatorSystem();
		cooperatorSystem.setSystemId(systemId);
		
		cooperatorSystemRepository.save(cooperatorSystem);
		return cooperatorSystem;
	}

	@Transactional
	public CooperatorSystem getCooperatorSystem(Integer systemId) {
		return cooperatorSystemRepository.findBysystemId(systemId);
	}

	@Transactional
	public List<CooperatorSystem> getAllCooperatorSystems() {
		return toList(cooperatorSystemRepository.findAll());
	}


	@Transactional
	public Document document(DocumentName docName, Integer docId) {
		Document document = new Document();
		document.setDocId(docId);
		document.setDocName(docName);

		documentRepository.save(document);

		return document;
	}

	@Transactional
	public List<Document> getAllDocuments(){
		return toList(documentRepository.findAll());
	}

	
	@Transactional
	public  CoopTerm createCoopTerm(Date startDate, Date endDate, Integer termId) {
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
	

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
	
	
	
	
	

	
	

