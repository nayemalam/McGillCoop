package ca.mcgill.ecse321.cooperator.dao;

import java.util.*;
import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.CoopTerm;
import ca.mcgill.ecse321.cooperator.model.Document;

public interface DocumentRepository extends CrudRepository<Document, Integer> {
	Document findBydocId(Integer id);
	List<Document> findByDueDate(Date dueDate);
	List<Document> findBycoopTerm(CoopTerm coopTerm);
}