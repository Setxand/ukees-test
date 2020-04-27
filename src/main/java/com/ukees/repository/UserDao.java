package com.ukees.repository;

import com.ukees.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.ukees.repository.EmployeeDao.EMPLOYEE_TABLE_NAME;

@Component
public class UserDao extends Dao {

	static final String USER_EMAIL_FIELD = "email";
	static final String USER_PASSWORD_FIELD = "password";
	static final String USER_PARAM_PREFIX = "u";

	private static final String USER_PARAMS_SELECTIONS_SECTION =
			pPrefix() + DOT + ID_FIELD + COMMA + pPrefix() + DOT +
					USER_EMAIL_FIELD + COMMA + pPrefix() + DOT + USER_PASSWORD_FIELD;

	private static final String USER_FROM_SELECT_SECTION = EMPLOYEE_TABLE_NAME + SPACE + USER_PARAM_PREFIX;
	/**
	 * SELECT USER BY EMAIL QUERY
	 */
	private static final String GET_USER_BY_EMAIL_QUERY = createSelectQuery(
			USER_PARAMS_SELECTIONS_SECTION,
			USER_FROM_SELECT_SECTION,
			conditionBy(USER_EMAIL_FIELD));

	/**
	 * SELECT USER BY ID QUERY
	 */
	private static final String GET_USER_BY_ID_QUERY = createSelectQuery(
			USER_PARAMS_SELECTIONS_SECTION,
			USER_FROM_SELECT_SECTION,
			conditionBy(ID_FIELD));

	private final RowMapper<User> userRowMapper;

	public UserDao(RowMapper<User> userRowMapper) {
		this.userRowMapper = userRowMapper;
	}

	public Optional<User> findByEmail(String email) {
		return Optional.of(getJdbcTemplate()
				.queryForObject(GET_USER_BY_EMAIL_QUERY, new Object[]{email}, userRowMapper));
	}

	public Optional<User> findById(String userId) {
		return Optional.of(getJdbcTemplate().queryForObject(GET_USER_BY_ID_QUERY, new Object[]{userId}, userRowMapper));
	}

	private static String conditionBy(String by) {
		return USER_PARAM_PREFIX + DOT + by + EQUALS + QUESTION_MARK;
	}

	private static String pPrefix() {
		return USER_PARAM_PREFIX;
	}
}
