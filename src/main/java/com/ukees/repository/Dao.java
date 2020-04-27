package com.ukees.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.ukees.repository.DepartmentDao.DEPARTMENT_NAME_FIELD;
import static com.ukees.repository.DepartmentDao.DEPARTMENT_TABLE_NAME;
import static com.ukees.repository.EmployeeDao.*;
import static com.ukees.repository.UserDao.USER_EMAIL_FIELD;
import static com.ukees.repository.UserDao.USER_PASSWORD_FIELD;

@Getter
@Primary
@Component
public abstract class Dao {

	protected static final String INPUT_VARIABLE = "%s";

	static final String QUESTION_MARK = "?";
	static final String EQUALS = " = ";
	static final String COMMA = ", ";
	static final String LEFT_BRACE = "(";
	static final String RIGHT_BRACE = ")";
	static final String SPACE = " ";
	static final String QUOTE = "'";
	static final String STAR = "*";
	static final String UNDERSCORE = "_";
	static final String ID_FIELD = "id";
	static final String DOT = ".";

	private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";
	private static final String FOREIGN_KEY = " FOREIGN KEY (%s) REFERENCES %s(%s)";
	private static final String SELECT_QUERY_CONDITIONAL = "SELECT %s FROM %s WHERE %s";
	private static final String SELECT_QUERY = "SELECT %s FROM %s";
	private static final String LEFT_JOIN = " LEFT JOIN %s ON %s";
	private static final String CREATE_TABLE_QUERY_PARAM = " create table if not exists ";
	private static final String ID_QUERY_PARAM = ID_FIELD + " varchar(36) not null PRIMARY KEY ";
	private static final String BOOLEAN_TYPE = " boolean";
	private static final String VARCHAR_TYPE = " varchar";
	private static final int VARCHAR_DEFAULT_LENGTH = 64;
	private static final int UUID_LENGTH = 36;

	@Autowired private JdbcTemplate jdbcTemplate;

	protected static String createTableQuery(String name, String... fields) {
		StringBuilder builder = new StringBuilder(CREATE_TABLE_QUERY_PARAM);
		builder.append(name).append(SPACE);
		builder.append(LEFT_BRACE).append(ID_QUERY_PARAM).append(COMMA);

		for (int i = 0; i < fields.length; i++) {
			builder.append(fields[i]);

			if (i != fields.length - 1) {
				builder.append(COMMA);
			}
		}


		builder.append(RIGHT_BRACE);
		return builder.toString();
	}

	protected static String varCharField(String fieldName) {
		return fieldName + VARCHAR_TYPE + LEFT_BRACE + VARCHAR_DEFAULT_LENGTH + RIGHT_BRACE;
	}

	protected static String varCharField(String fieldName, int length) {
		return fieldName + VARCHAR_TYPE + LEFT_BRACE + length + RIGHT_BRACE;
	}

	protected static String boolField(String fieldName) {
		return fieldName + BOOLEAN_TYPE;
	}

	protected static String quotes(String id) {
		if (StringUtils.isEmpty(id))
			return null;

		return QUOTE + id + QUOTE;
	}

	protected static String createInsertQuery(String tableName, String columns, String values) {
		return String.format(INSERT_QUERY, tableName, columns, values);
	}

	protected static String foreignKey(String foreignKeyFieldName, String referenceTableName, String idFieldName) {
		return String.format(FOREIGN_KEY, foreignKeyFieldName, referenceTableName, idFieldName);
	}

	protected static String createLeftJoinQuery(String tableName, String on) {
		return String.format(LEFT_JOIN, tableName, on);
	}

	protected static String createSelectQuery(String selectionSection, String fromSection, String conditionSection) {
		return String.format(SELECT_QUERY_CONDITIONAL, selectionSection, fromSection, conditionSection);
	}

	protected static String createSelectQuery(String selectionSection, String fromSection) {
		return String.format(SELECT_QUERY, selectionSection, fromSection);
	}

	@PostConstruct
	public void createTables() {
		// Create department table
		getJdbcTemplate().execute(createTableQuery(DEPARTMENT_TABLE_NAME, varCharField(DEPARTMENT_NAME_FIELD)));

		// Create employee table
		getJdbcTemplate().execute(createTableQuery(EMPLOYEE_TABLE_NAME,
				varCharField(USER_EMAIL_FIELD), varCharField(USER_PASSWORD_FIELD),
				varCharField(EMPLOYEE_NAME_FIELD), boolField(EMPLOYEE_ACTIVE_FIELD),
				varCharField(EMPLOYEE_DEPARTMENT_FOREIGN_KEY_FIELD, UUID_LENGTH) + COMMA +
						foreignKey(EMPLOYEE_DEPARTMENT_FOREIGN_KEY_FIELD, DEPARTMENT_TABLE_NAME, ID_FIELD)));
	}
}
