package com.ukees.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Employee extends User {

	private Boolean active;
	private String departmentId;

}
