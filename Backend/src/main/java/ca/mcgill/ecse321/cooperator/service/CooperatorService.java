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
import ca.mcgill.ecse321.cooperator.dao.UserRepository;

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
	UserRepository userRepository;
	@Autowired
	DocumentRepository documentRepository;
	
	
	@Transactional
	public SystemUser createUser(Integer id, String name, String fName, String emailAddress, String userName, String password) {
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Person name cannot be empty!");
		}
		SystemUser user = new Student();
		user.setLastName(name);
		user.setFirstName(fName);
		user.setEmailAddress(emailAddress);
		user.setUserName(userName);
		user.setPassword(password);
		userRepository.save(user);
		return user;
	}
	
	@Transactional
	public SystemUser getUser(Integer id) {
		SystemUser user = userRepository.findUserById(id);
		return user;
	}

	@Transactional
	public List<SystemUser> getAllUsers() {
		return toList(userRepository.findAll());
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
	
	
	
	
	

	
	

