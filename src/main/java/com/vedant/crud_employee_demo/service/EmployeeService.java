package com.vedant.crud_employee_demo.service;

import com.vedant.crud_employee_demo.entity.Employee;
import com.vedant.crud_employee_demo.rest.EmployeeRestController;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    public Employee findById(int id);

    public Employee save(Employee employee);

    public void deleteEmployee(int id);

}
