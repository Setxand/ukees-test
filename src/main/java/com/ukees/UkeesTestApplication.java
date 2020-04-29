package com.ukees;

import com.ukees.model.Department;
import com.ukees.model.Employee;
import com.ukees.model.User;
import com.ukees.repository.DepartmentRowMapper;
import com.ukees.repository.EmployeeRowMapper;
import com.ukees.repository.UserRowMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UkeesTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UkeesTestApplication.class, args);
	}


	@Bean
	public RowMapper<Department> departmentRowMapper(RowMapper<Employee> employeeRowMapper) {
		return new DepartmentRowMapper(employeeRowMapper);
	}

	@Bean
	public RowMapper<Employee> getEmployeeRowMapper() {
		return new EmployeeRowMapper();
	}

	@Bean
	public RowMapper<User> getUserRowMapper() {
		return new UserRowMapper();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
