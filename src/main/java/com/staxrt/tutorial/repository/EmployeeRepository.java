package com.staxrt.tutorial.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.staxrt.tutorial.model.Desks;
import com.staxrt.tutorial.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query(value="select * FROM employees  left JOIN desks  ON desks.id=employees.desk_id WHERE desks.desk_name IS NULL",nativeQuery=true)
    Collection<Employee> findNonAssociatedEmployees();
	
	@Query(value="SELECT * FROM employees left JOIN desks on desks.id=employees.desk_id WHERE desks.desk_name is not NULL",nativeQuery=true)
    Collection<Employee> findAssociatedEmployees();
}
