package com.vedant.crud_employee_demo.service;

import com.vedant.crud_employee_demo.dao.EmployeeRepository;
import com.vedant.crud_employee_demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
        employeeRepository = theEmployeeRepository;
    }


    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {

        Optional<Employee> result = employeeRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            return null; // let controller handle this case
        }
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }


    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }


}
