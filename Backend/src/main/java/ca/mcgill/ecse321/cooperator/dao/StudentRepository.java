package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.cooperator.model.Student;




public interface StudentRepository extends CrudRepository<Student, Integer> {
	
	Student findByuserID(Integer id);
	List<Student> findByStudentId(Integer studentId);
	List<Student> findByProgram(String program);
	List<Student> deleteByuserID(Integer id);
}