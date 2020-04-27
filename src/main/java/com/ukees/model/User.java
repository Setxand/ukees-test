package com.ukees.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel {

	public enum UserRole {
		ROLE_EMPLOYEE
	}

	private String email;
	private String password;
	private UserRole role = UserRole.ROLE_EMPLOYEE;

}
