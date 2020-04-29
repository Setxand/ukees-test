package com.ukees.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukees.dto.EmployeeDTO;
import com.ukees.service.EmployeeService;
import com.ukees.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class EmployeeController {

	@Autowired EmployeeService employeeService;
	@Autowired ObjectMapper objectMapper;

	@GetMapping("/v1/employees")
	public Page<EmployeeDTO> getEmployees(Pageable pageable, @RequestParam(defaultValue = "") String search) {
		return StringUtils.isEmpty(search) ? employeeService.getEmployees(pageable).map(DTOUtil::employee) :
				employeeService.searchByName(search, pageable).map(DTOUtil::employee);
	}

	@GetMapping("/v1/employees/{employeeId}")
	public EmployeeDTO getEmployeeById(@PathVariable String employeeId) {
		return DTOUtil.employee(employeeService.getEmployee(employeeId));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PatchMapping("/v1/employees")
	public void getEmployeeById(@RequestBody EmployeeDTO dto) {
		employeeService.editEmployee(dto);
	}

	@DeleteMapping("/{employeeId}")
	public void deleteEmployee(@PathVariable String employeeId) {

	}
}
