package com.ukees.repository;

import com.ukees.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
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

	static final String ROW_COUNT_NAME = "row_count";


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


	/**
	 * FIND ALL EMPLOYEES PAGEABLE QUERY
	 */
	private static final String FIND_ALL_QUERY_PAGEABLE = createSelectQuery(
			STAR,
			EMPLOYEE_TABLE_NAME + SPACE + ORDER_BY + ID_FIELD + LIMIT + SPACE +
					INPUT_VARIABLE + SPACE + OFFSET + SPACE + INPUT_VARIABLE);
	/**
	 * TOTAL COUNT OF EMPLOYEES QUERY
	 */
	private static final String TOTAL_QUERY = createSelectQuery(
			COUNT + LEFT_BRACE + STAR + RIGHT_BRACE + SPACE + AS + SPACE + ROW_COUNT_NAME,
			EMPLOYEE_TABLE_NAME);

	/**
	 * 	UPDATE EMPLOYEE QUERY
	 */
	private static final String UPDATE_EMPLOYEE_QUERY = createUpdateQuery(
			EMPLOYEE_TABLE_NAME,
			INPUT_VARIABLE,
			ID_FIELD + EQUALS + quotes(INPUT_VARIABLE));


	/**
	 * DELETE EMPLOYEE QUERY
	 */
	private static final String DELETE_EMPLOYEE_QUERY =
			createDeleteQuery(EMPLOYEE_TABLE_NAME, ID_FIELD + EQUALS + quotes(INPUT_VARIABLE));

	/**
	 * TOTAL COUNT OF EMPLOYEES QUERY CONDITIONAL
	 */
	private String getTotalForSearchQuery(String search) {
		return createSelectQuery(
				COUNT + LEFT_BRACE + STAR + RIGHT_BRACE + SPACE + AS + SPACE + ROW_COUNT_NAME,
				EMPLOYEE_TABLE_NAME,
				EMPLOYEE_NAME_FIELD + LIKE + quotes(PERCENT + search));
	}

	/**
	 * SEARCH EMPLOYEES PAGEABLE QUERY
	 */
	private static String getSearchQueryPageable(String search, int pageSize, long offset) {
		return createSelectQuery(STAR,
				EMPLOYEE_TABLE_NAME,
				EMPLOYEE_NAME_FIELD + LIKE + quotes(PERCENT + search + PERCENT) +
						ORDER_BY + ID_FIELD + LIMIT + SPACE +
						pageSize + SPACE + OFFSET + SPACE + offset);
	}



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

	public Page<Employee> findAll(Pageable pageable) {
		int total = getTotalEmployees();
		List<Employee> employees = getJdbcTemplate()
				.query(String.format(FIND_ALL_QUERY_PAGEABLE,
						pageable.getPageSize(), pageable.getOffset()), DaoUtil::employee);
		return new PageImpl<>(employees, pageable, total);
	}

	public Page<Employee> searchByName(String search, Pageable pageable) {
		int total = getJdbcTemplate().query(getTotalForSearchQuery(search), (rs, rowNum) -> rs.getInt(1)).get(0);
		List<Employee> employees = getJdbcTemplate()
				.query(getSearchQueryPageable(search, pageable.getPageSize(), pageable.getOffset()), DaoUtil::employee);
		return new PageImpl<>(employees, pageable, total);
	}

	public void updateEmployee(Employee employee) {
		getJdbcTemplate().execute(String.format(UPDATE_EMPLOYEE_QUERY, employee, employee.getId()));
	}

	public void deleteEmployeeById(String employeeId) {
		getJdbcTemplate().execute(String.format(DELETE_EMPLOYEE_QUERY, employeeId));
	}

	private int getTotalEmployees() {
		List<Integer> query = getJdbcTemplate().query(TOTAL_QUERY, (rs, rowNum) -> rs.getInt(1));
		assert query.size() == 1;

		return query.get(0);
	}
}
