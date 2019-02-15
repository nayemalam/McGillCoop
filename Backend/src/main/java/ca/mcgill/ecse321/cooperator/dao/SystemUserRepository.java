package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.SystemUser;


public interface SystemUserRepository extends CrudRepository<SystemUser, Integer> {
	
	SystemUser findByuserID(Integer id);

}
