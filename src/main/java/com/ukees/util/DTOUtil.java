package com.ukees.util;

import com.ukees.dto.DepartmentDTO;
import com.ukees.dto.EmployeeDTO;
import com.ukees.model.Department;
import com.ukees.model.Employee;

import java.util.stream.Collectors;

public class DTOUtil {

	public static DepartmentDTO department(Department model) {
		DepartmentDTO dto = new DepartmentDTO();
		dto.id = model.getId();
		dto.name = model.getName();
		dto.employees = model.getEmployees().stream().map(DTOUtil::employee).collect(Collectors.toList());
		return dto;
	}

	public static EmployeeDTO employee(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.id = employee.getId();
		employeeDTO.name = employee.getName();
		employeeDTO.departmentId = employee.getDepartmentId();
		employeeDTO.isActive = employee.getActive();

		return employeeDTO;
	}
}
