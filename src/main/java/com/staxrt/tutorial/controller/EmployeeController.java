package com.staxrt.tutorial.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staxrt.tutorial.exception.ResourceNotFoundException;

import com.staxrt.tutorial.model.Employee;

import com.staxrt.tutorial.repository.DeskRepository;
import com.staxrt.tutorial.repository.EmployeeRepository;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	  @Autowired
	  private DeskRepository deskRepository;
	  
	  @Autowired
	  private EmployeeRepository employeeRepository;

	 
	  @GetMapping("/employees")
	  public List<Employee> getAllEmployee() {
	    return employeeRepository.findAll();
	  }

	  
	  @GetMapping("/desks/{id}/employees")
	  public ResponseEntity<Employee> getEmployeeByDeskId(@PathVariable(value = "id") Long deskId)
	      throws ResourceNotFoundException {
		  
			        deskRepository
			            .findById(deskId)
			            .orElseThrow(() -> new ResourceNotFoundException("Desk not found on :: " + deskId));
		  
		  List<Employee> employees=employeeRepository.findAll();
		  List<Employee> employee=new ArrayList<>();
		  if(employees.size()!=0) {
	    
			  employee =
	       employees.stream().filter(floor -> floor.getDeskId()==deskId).collect(Collectors.toList());
	    
			  System.out.println(employee);
	    if(employee.size() != 0)
	    
	    	return ResponseEntity.ok().body(employee.get(0));
	    
	    else {
	    	throw new ResourceNotFoundException("Employee Not Found at "+deskId);
	    }
		  }else {
			  throw new ResourceNotFoundException("Employee not Found At "+deskId);
		  }
	  }
	 
	  @PostMapping("/desks/{id}/employees")
	  public ResponseEntity<Employee> createEmployee(
	      @PathVariable(value = "id") Long deskId, @Valid @ModelAttribute Employee employeeDetails)
	      throws ResourceNotFoundException {
			        deskRepository
			            .findById(deskId)
			            .orElseThrow(() -> new ResourceNotFoundException("Desk not found on :: " + deskId));
		  employeeDetails.setDeskId(deskId);
	    final Employee updatedEmployee = employeeRepository.save(employeeDetails);
	    return ResponseEntity.ok().body(updatedEmployee);
	  }

	  @PostMapping("/employees")
	  public ResponseEntity<Employee> createNonEmployee(
	      @Valid @ModelAttribute Employee employeeDetails)
	      throws ResourceNotFoundException {
	    final Employee updatedEmployee = employeeRepository.save(employeeDetails);
	    return ResponseEntity.ok().body(updatedEmployee);
	  }
	  
	  @GetMapping("/employees/associations")
	  public ResponseEntity<Collection<Employee>> findAssociatedDesks(){
		  Collection<Employee> employees=employeeRepository.findAssociatedEmployees();
		  return ResponseEntity.ok().body(employees);
		  
	  }
	  
	  @GetMapping("/employees/nonassociations")
	  public ResponseEntity<Collection<Employee>> findNonAssociatedDesks(){
		 Collection<Employee> employees= employeeRepository.findNonAssociatedEmployees();
		  return ResponseEntity.ok().body(employees);
		  
	  }

}
