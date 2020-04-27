package com.ukees.repository;

import com.ukees.model.Department;
import com.ukees.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ukees.repository.Dao.UNDERSCORE;
import static com.ukees.repository.DepartmentDao.DEPARTMENT_FIELD_PREFIX;

public class DepartmentRowMapper implements RowMapper<Department> {

	private static final int NEXT_ROW = 1;
	private final RowMapper<Employee> employeeRowMapper;

	public DepartmentRowMapper(RowMapper<Employee> employeeRowMapper) {
		this.employeeRowMapper = employeeRowMapper;
	}

	@Override
	public Department mapRow(ResultSet resultSet, int i) throws SQLException {
		Department department = new Department();

		department.setId(resultSet.getString(DEPARTMENT_FIELD_PREFIX + UNDERSCORE + Dao.ID_FIELD));
		department.setName(resultSet.getString(DEPARTMENT_FIELD_PREFIX + UNDERSCORE +
				DepartmentDao.DEPARTMENT_NAME_FIELD));

		department.getEmployees().add(employeeRowMapper.mapRow(resultSet, i));

		if (resultSet.next()) {
			department.getEmployees().add(employeeRowMapper.mapRow(resultSet, i + NEXT_ROW));
		}
		return department;
	}
}
