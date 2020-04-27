package com.ukees.controller;

import com.ukees.dto.DepartmentDTO;
import com.ukees.service.DepartmentService;
import com.ukees.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class DepartmentController {

	@Autowired DepartmentService departmentService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/v1/departments")
	public DepartmentDTO createDepartment(@RequestBody DepartmentDTO dto) {
		return DTOUtil.department(departmentService.createDepartment(dto));
	}

	@GetMapping("/v1/departments/{departmentId}")
	public DepartmentDTO getDepartmentById(@PathVariable String departmentId) {
		return DTOUtil.department(departmentService.getDepartmentById(departmentId));
	}

	@GetMapping("/v1/departments-names")
	public List<DepartmentDTO> getDepartments(Pageable pageable) {
		return departmentService.getDepartments(pageable).stream().map(DTOUtil::department).collect(Collectors.toList());
	}
}
