package com.ukees.repository;

import com.ukees.model.Department;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.ukees.repository.EmployeeDao.*;

@Component
public class DepartmentDao extends Dao {

	public static final String DEPARTMENT_TABLE_NAME = "department";
	static final String DEPARTMENT_NAME_FIELD = "name";
	static final String DEPARTMENT_FIELD_PREFIX = "d";
	private static final String[] FIELD_NAMES = {ID_FIELD, DEPARTMENT_NAME_FIELD};


	private static final String LEFT_JOIN_JOR_DEPARTMENT_BY_ID_SECTION = createLeftJoinQuery(
			EMPLOYEE_TABLE_NAME + SPACE + EMPLOYEE_FIELD_PREFIX + SPACE, DEPARTMENT_FIELD_PREFIX + DOT + ID_FIELD +
					EQUALS + EMPLOYEE_FIELD_PREFIX + DOT + EMPLOYEE_DEPARTMENT_FOREIGN_KEY_FIELD);
	private static final String DEPARTMENT_BY_ID_FIELD_SELECTION_SECTION =
			"d.id as d_id, e.id as e_id, d.name as d_name, e.name as e_name, e.active, e.department_id";
	/**
	 * SELECT DEPARTMENT BY ID QUERY
	 */
	private static final String GET_DEPARTMENT_BY_ID_QUERY = createSelectQuery(
			DEPARTMENT_BY_ID_FIELD_SELECTION_SECTION,
			DEPARTMENT_TABLE_NAME + SPACE + DEPARTMENT_FIELD_PREFIX + LEFT_JOIN_JOR_DEPARTMENT_BY_ID_SECTION,
			DEPARTMENT_FIELD_PREFIX + DOT + ID_FIELD + EQUALS + QUESTION_MARK);

	/**
	 * INSERT NEW DEPARTMENT QUERY
	 */
	private static final String INSERT_DEPARTMENT_QUERY =
			createInsertQuery(DEPARTMENT_TABLE_NAME, String.join(COMMA, FIELD_NAMES), INPUT_VARIABLE);


	/**
	 * SELECT DEPARTMENT NAMES ONLY
	 */
	private static final String GET_DEPARTMENTS_QUERY = createSelectQuery(STAR, DEPARTMENT_TABLE_NAME);


	private final RowMapper<Department> rowMapper;

	public DepartmentDao(RowMapper<Department> rowMapper) {
		this.rowMapper = rowMapper;
	}

	public Department save(Department department) {
		department.setId(UUID.randomUUID().toString());

		String employeeValues = quotes(department.getId()) + COMMA +
				quotes(department.getName());

		getJdbcTemplate().execute(insert(INSERT_DEPARTMENT_QUERY, employeeValues));

		return department;
	}

	public Department getDepartmentById(String departmentId) {
		return getJdbcTemplate()
				.queryForObject(GET_DEPARTMENT_BY_ID_QUERY, new Object[]{departmentId}, rowMapper);
	}

	public List<Department> getDepartments() {
		return getJdbcTemplate().query(GET_DEPARTMENTS_QUERY, (rs, rowNum) ->
			new Department(rs.getString(ID_FIELD), rs.getString(DEPARTMENT_NAME_FIELD))
		);
	}

	private String insert(String insertDepartmentQuery, String employeeValues) {
		return String.format(insertDepartmentQuery, employeeValues);
	}
}

