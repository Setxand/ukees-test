package com.ukees.repository;

import com.ukees.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ukees.repository.Dao.ID_FIELD;
import static com.ukees.repository.UserDao.USER_EMAIL_FIELD;
import static com.ukees.repository.UserDao.USER_PASSWORD_FIELD;

public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet resultSet, int i) throws SQLException {

		User user = new User();
		user.setId(resultSet.getString(ID_FIELD));
		user.setEmail(resultSet.getString(USER_EMAIL_FIELD));
		user.setPassword(resultSet.getString(USER_PASSWORD_FIELD));
		return user;
	}
}
