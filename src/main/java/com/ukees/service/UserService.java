package com.ukees.service;

import com.ukees.repository.UserDao;
import com.ukees.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private static final String INVALID_USER_EMAIL_MESSAGE = "Invalid User Email";
	private static final String INVALID_USER_ID_MESSAGE = "Invalid User ID";
	private final UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(INVALID_USER_EMAIL_MESSAGE));
	}

	public User getUser(String userId) {
		return userDao.findById(userId).orElseThrow(() -> new IllegalArgumentException(INVALID_USER_ID_MESSAGE));
	}
}
