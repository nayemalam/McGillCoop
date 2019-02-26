package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.cooperator.model.CoopAdministrator;



public interface CoopAdministratorRepository extends CrudRepository<CoopAdministrator, Integer> {

	CoopAdministrator findByuserID(Integer id);
	
	CoopAdministrator findByemailAddress(String emailAddress);
	
}
