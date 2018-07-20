package com.chathumina.controller;


import com.chathumina.model.Employee;
import com.chathumina.service.EmployeeService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/", headers = "Accept=application/json")
    @ResponseBody
    public List<Employee> listEmployee() throws IOException {
        List<Employee> listEmployee = employeeService.getAllEmployees();
        return listEmployee;
    }

    @RequestMapping(value = "/newEmployee/{Name}/{Address}/{Email}/{Number}",  headers = "Accept=application/json")
    @ResponseBody
    public List<Employee> newContact(@PathVariable String Name, @PathVariable String Address, @PathVariable String Email, @PathVariable String Number) {
        Employee employee = new Employee();
        employee.setName(Name);
        employee.setAddress(Address);
        employee.setEmail(Email);
        employee.setTelephone(Number);

        employeeService.addEmployee(employee);
        List<Employee> list = employeeService.getAllEmployees();

        return list;
    }


    @RequestMapping(value = "/deleteEmployee/{Id}")
    @ResponseBody
    public List<Employee> deleteEmployee(@PathVariable int Id) {
        employeeService.deleteEmployee(Id);
        List<Employee> list = employeeService.getAllEmployees();
        return list;
    }

    @RequestMapping(value = "/editEmployee")
    public ModelAndView editContact(HttpServletRequest request) {
        int employeeId = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeService.getEmployee(employeeId);
        ModelAndView model = new ModelAndView("EmployeeForm");
        model.addObject("employee", employee);

        return model;
    }

    @RequestMapping(value = "/searchUser/{Name}/{Id}")
    public Employee searchUser(@PathVariable String Name, @PathVariable int Id) {
        List<Employee> list = employeeService.getAllEmployees();

        for(Employee lst:list){
            if(lst.getName().equals(Name) && lst.getId()==Id){
                return lst;
            }
        }

        return null;
    }

}