package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.User;


public interface UserRepository extends CrudRepository<User, String> {
	
	User findUserBylasName(String name);

}
