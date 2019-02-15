package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.SystemUser;


public interface UserRepository extends CrudRepository<SystemUser, String> {
	
	SystemUser findUserBylastName(String name);

}
