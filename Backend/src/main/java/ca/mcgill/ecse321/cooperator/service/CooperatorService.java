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
	
	
	//==========================================================================================
	// generic SystemUser CRUD transactions

	@Transactional
	public SystemUser getUser(Integer id) {
		SystemUser user = systemUserRepository.findUserByuserID(id);
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
		SystemUser user = systemUserRepository.findUserByuserID(id);
		//TODO
		return false;
	}

	//==========================================================================================

	//==========================================================================================
	// Student CRUD transactions
	
	@Transactional
	public SystemUser createStudent(Integer id, String name, String fName, String emailAddress, String userName, String password, Integer studentId) {
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
		systemUserRepository.save(user);
		return user;
	}

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
	
	
	
	
	

	
	

