package com.vedant.crud_employee_demo.dao;

import com.vedant.crud_employee_demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
