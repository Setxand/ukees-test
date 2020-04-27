package com.ukees.service;

import com.ukees.repository.EmployeeDao;
import com.ukees.model.Employee;
import com.ukees.security.dto.JwtRequest;
import org.springframework.stereotype.Service;

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
}
