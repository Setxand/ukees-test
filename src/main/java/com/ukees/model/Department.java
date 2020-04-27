package com.ukees.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Department extends BaseModel{

	private List<Employee> employees = new ArrayList<>();


	public Department(String id, String name) {
		this.setId(id);
		this.setName(name);
	}
}
