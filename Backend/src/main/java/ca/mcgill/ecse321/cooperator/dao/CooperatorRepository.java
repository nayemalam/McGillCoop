package ca.mcgill.ecse321.cooperator.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.model.User;

@Repository
public class CooperatorRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public User createPerson(String name) {
		User p = new Student();
		p.setName(name);
		entityManager.persist(p);
		return p;
	}

}
