package com.ukees.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukees.dto.EmployeeDTO;
import com.ukees.service.EmployeeService;
import com.ukees.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class EmployeeController {

	@Autowired EmployeeService employeeService;
	@Autowired ObjectMapper objectMapper;

	@GetMapping
	public void getEmployees() {

	}

	@GetMapping("/v1/employees/{employeeId}")
	public EmployeeDTO getEmployeeById(@PathVariable String employeeId) {
		return DTOUtil.employee(employeeService.getEmployee(employeeId));
	}

	@DeleteMapping("/{employeeId}")
	public void deleteEmployee(@PathVariable String employeeId) {

	}

	@PatchMapping("/{employeeId}")
	public void updateEmployee() {

	}

}
