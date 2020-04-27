package com.ukees.repository;

import com.ukees.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.ukees.repository.UserDao.USER_EMAIL_FIELD;
import static com.ukees.repository.UserDao.USER_PASSWORD_FIELD;

@Component
public class EmployeeDao extends Dao {

	static final String EMPLOYEE_TABLE_NAME = "employee";
	static final String EMPLOYEE_FIELD_PREFIX = "e";

	static final String EMPLOYEE_NAME_FIELD = "name";
	static final String EMPLOYEE_ACTIVE_FIELD = "active";
	static final String EMPLOYEE_DEPARTMENT_FOREIGN_KEY_FIELD = "department_id";


	private static final String[] FIELD_NAMES = {ID_FIELD, USER_EMAIL_FIELD,
			USER_PASSWORD_FIELD, EMPLOYEE_NAME_FIELD,
			EMPLOYEE_ACTIVE_FIELD, EMPLOYEE_DEPARTMENT_FOREIGN_KEY_FIELD};


	/**
	 * SELECT EMPLOYEE BY ID QUERY
	 */
	private static final String EMPLOYEE_BY_ID_QUERY = createSelectQuery(
			STAR,
			EMPLOYEE_TABLE_NAME + SPACE + EMPLOYEE_FIELD_PREFIX,
			EMPLOYEE_FIELD_PREFIX + DOT + ID_FIELD + EQUALS + QUESTION_MARK);

	private final RowMapper<Employee> rowMapper;
	private final PasswordEncoder passwordEncoder;

	public EmployeeDao(RowMapper<Employee> rowMapper, PasswordEncoder passwordEncoder) {
		this.rowMapper = rowMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public Employee save(Employee employee) {
		employee.setId(UUID.randomUUID().toString());

		String employeeVal = quotes(employee.getId()) + COMMA +
				quotes(employee.getEmail()) + COMMA +
				quotes(passwordEncoder.encode(employee.getPassword())) + COMMA +
				quotes(employee.getName()) + COMMA +
				employee.getActive() + COMMA +
				quotes(employee.getDepartmentId());

		getJdbcTemplate().execute(createInsertQuery(EMPLOYEE_TABLE_NAME, String.join(COMMA, FIELD_NAMES), employeeVal));
		return employee;
	}

	public Employee getEmployeeById(String employeeId) {
		return getJdbcTemplate()
				.queryForObject(EMPLOYEE_BY_ID_QUERY, new Object[]{employeeId}, rowMapper);
	}
}
