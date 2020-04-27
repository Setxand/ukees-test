package com.ukees.service;

import com.ukees.repository.DepartmentDao;
import com.ukees.dto.DepartmentDTO;
import com.ukees.model.Department;
import com.ukees.util.DTOUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.soap.Detail;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

	private final DepartmentDao departmentDao;

	public DepartmentService(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public Department createDepartment(DepartmentDTO dto) {
		Department department = new Department();
		department.setName(dto.name);

		return departmentDao.save(department);
	}

	public Department getDepartmentById(String departmentId) {
		return departmentDao.getDepartmentById(departmentId);
	}

	public List<Department> getDepartments(Pageable pageable) {
		return departmentDao.getDepartments();
	}
}
