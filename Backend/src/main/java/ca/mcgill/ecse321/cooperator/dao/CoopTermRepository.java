package ca.mcgill.ecse321.cooperator.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;

public interface CoopTermRepository extends CrudRepository<CoopTerm, Integer> {
	
	CoopTerm findBytermId(Integer termId);
	List<CoopTerm> findByStudent(Integer userId);
	List<CoopTerm> findByEmployer(Integer userId);
	List<CoopTerm> findByStartDate(Date startDate);

}
