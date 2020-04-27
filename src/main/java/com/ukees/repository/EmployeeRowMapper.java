package com.ukees.repository;

import com.ukees.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ukees.repository.Dao.UNDERSCORE;
import static com.ukees.repository.EmployeeDao.EMPLOYEE_FIELD_PREFIX;

public class EmployeeRowMapper implements RowMapper<Employee> {
	@Override
	public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
		Employee employee = new Employee();
		employee.setId(resultSet.getString(EMPLOYEE_FIELD_PREFIX + UNDERSCORE + Dao.ID_FIELD));
		employee.setName(resultSet.getString(EMPLOYEE_FIELD_PREFIX + UNDERSCORE + EmployeeDao.EMPLOYEE_NAME_FIELD));
		employee.setActive(resultSet.getBoolean(EmployeeDao.EMPLOYEE_ACTIVE_FIELD));
		employee.setDepartmentId(resultSet.getString(EmployeeDao.EMPLOYEE_DEPARTMENT_FOREIGN_KEY_FIELD));

		return employee;
	}
}
