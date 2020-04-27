package com.ukees.service;

import com.ukees.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginSessionService {


	public boolean validateSession(String jwtToken) {
		return true;
	}

	public void createSession(String userEmail, String email, String token) {

	}

	public void logout(String substring) {

	}

	public void refreshSession(User userId, String accessToken, String refreshToken) {
	}
}
