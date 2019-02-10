package ca.mcgill.ecse321.cooperator.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator.model.Student;

@Repository
public class CooperatorRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Student createPerson(int name) {
		Student p = new Student();
		p.setStudentId(name);
		entityManager.persist(p);
		return p;
	}

}
