package com.ukees.service;

import com.ukees.dto.EmployeeDTO;
import com.ukees.repository.EmployeeDao;
import com.ukees.model.Employee;
import com.ukees.security.dto.JwtRequest;
import com.ukees.util.DTOUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

	private final EmployeeDao employeeDao;

	public EmployeeService(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public Employee createEmployee(JwtRequest request) {
		Employee employee = new Employee();
		employee.setActive(true);
		employee.setName(request.name);
		employee.setDepartmentId(request.departmentId);
		employee.setEmail(request.email);
		employee.setPassword(request.password);

		return employeeDao.save(employee);
	}

	public Employee getEmployee(String employeeId) {
		return employeeDao.getEmployeeById(employeeId);
	}

	public Page<Employee> getEmployees(Pageable pageable) {
		return employeeDao.findAll(pageable);
	}

	public Page<Employee> searchByName(String search, Pageable pageable) {
		return employeeDao.searchByName(search, pageable);
	}

	public void editEmployee(EmployeeDTO dto) {
		Employee employee = DTOUtil.toEmployee(dto);

		employeeDao.updateEmployee(employee);
	}
}
