package com.vedant.crud_employee_demo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vedant.crud_employee_demo.entity.Employee;
import com.vedant.crud_employee_demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {


    private EmployeeService employeeService;
    private ObjectMapper objectMapper;


    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService, ObjectMapper theObjectMapper) {
        employeeService  =theEmployeeService;
        objectMapper = theObjectMapper;
    }




    @GetMapping("/employees")
    public List<Employee> findAllEmployees() {
        return employeeService.findAll();
    }


    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId ) {
        Employee employee = employeeService.findById(employeeId);

        if(employee == null) throw new RuntimeException("Employee does not exist");
        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){

        //incase they pss some id in json, set that id to 0
        //this is to force save of the new item instead of an update


        theEmployee.setId(0);

        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //add mapping for updating the employees PUT request

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //add patch mapping for employees  /employees/{employeeid} - patching an employee...partial update
    @PatchMapping("/employees/{employeeid}")
    public Employee patchEmployee(@PathVariable int employeeid,
                                  @RequestBody Map<String, Object> patchPayload){
        Employee tempEmployee = employeeService.findById(employeeid);

        if(tempEmployee == null){
            throw new RuntimeException("Employee id not found: "+ employeeid);
        }

        //throw exception if request body contains "id" key
        if(patchPayload.containsKey("id")){
            throw new RuntimeException("Employee id not allowed in request body! ");
        }

        Employee patchedEmployee = apply(patchPayload, tempEmployee);
        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    public Employee apply(Map<String, Object> patchPayload, Employee tempEmployee) {
        //convert an employee object into a JSON object node

        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

        //convert patchedPayLoad into JSON object node
        ObjectNode patchedNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        //merge the patch updates to employee node
        employeeNode.setAll(patchedNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }


    @DeleteMapping("/employee/{employeeid}")
    public String deleteEmployee(@PathVariable int employeeid){
        Employee tempEmployee = employeeService.findById(employeeid);
        if(tempEmployee == null){
            throw new RuntimeException("Employee not found!" + employeeid);
        }

        employeeService.deleteEmployee(employeeid);

        return "Deleted employee "+ employeeid;
    }


}
