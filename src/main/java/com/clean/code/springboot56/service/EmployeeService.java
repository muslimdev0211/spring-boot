package com.clean.code.springboot56.service;

import com.clean.code.springboot56.domain.Employee;
import com.clean.code.springboot56.repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }
    public List <Employee> findAll(){
        return employeeRepository.findAll();
    }

    public List<Employee> findByname(String name) {
        return employeeRepository.findByQuery(name);
    }
    @Scheduled(cron = "0 10  16 * * *")
    public Employee saveSchedule(){
        Employee employee = new Employee();
        employee.setName("Qurbon");
        employee.setLast_name("Kodirov");
        return employeeRepository.save(employee);
    }
}
