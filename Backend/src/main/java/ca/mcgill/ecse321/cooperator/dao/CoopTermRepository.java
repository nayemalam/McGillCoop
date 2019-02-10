package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;

public interface CoopTermRepository extends CrudRepository<CoopTerm, Integer> {
	
	CoopTerm findBytermId(Integer id);

}
