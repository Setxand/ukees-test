package com.ukees.repository;

import com.ukees.model.Department;
import com.ukees.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ukees.repository.Dao.ID_FIELD;
import static com.ukees.repository.DepartmentDao.DEPARTMENT_NAME_FIELD;
import static com.ukees.repository.EmployeeDao.*;

public class DaoUtil {
	public static Department department(ResultSet rs, int rowNum) throws SQLException {
		Department department = new Department();
		department.setId(rs.getString(ID_FIELD));
		department.setName(rs.getString(DEPARTMENT_NAME_FIELD));

		return department;
	}

	public static Employee employee(ResultSet resultSet, int i) throws SQLException {
		Employee employee = new Employee();
		employee.setId(resultSet.getString(ID_FIELD));
		employee.setName(resultSet.getString(EMPLOYEE_NAME_FIELD));
		employee.setActive(resultSet.getBoolean(EMPLOYEE_ACTIVE_FIELD));
		employee.setDepartmentId(resultSet.getString(EMPLOYEE_DEPARTMENT_FOREIGN_KEY_FIELD));
		return employee;
	}
}
