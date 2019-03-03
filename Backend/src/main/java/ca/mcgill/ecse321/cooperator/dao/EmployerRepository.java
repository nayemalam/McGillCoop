package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.cooperator.model.Employer;

public interface EmployerRepository extends CrudRepository<Employer, Integer> {
	
    Employer findByuserID(Integer id);
    Employer findByLastName(String name); 
    List<Employer> findByCompanyName(String companyName);

    
	


}