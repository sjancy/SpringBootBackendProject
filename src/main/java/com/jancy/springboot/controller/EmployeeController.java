package com.jancy.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jancy.springboot.exception.ResourceNotFoundException;
import com.jancy.springboot.model.Employee;
import com.jancy.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository  employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployess(){
		return employeeRepository.findAll();
	}
	
	//create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Employee not exist with id :" +id));
		return ResponseEntity.ok(employee);
	}
	
	//update employee rest api
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> upadteEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		
		Employee employee = employeeRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Employee not exist with id :" +id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee upadtedEmployee=employeeRepository.save(employee);
		return ResponseEntity.ok(upadtedEmployee);
	}
	
	//delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Employee not exist with id :" +id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response=new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	
	

}
