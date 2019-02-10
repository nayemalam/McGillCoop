package ca.mcgill.ecse321.cooperator.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator.model.User;

@Repository
public class CooperatorRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public User createPerson(String name) {
		User p = new User();
		p.setName(name);
		entityManager.persist(p);
		return p;
	}

}
