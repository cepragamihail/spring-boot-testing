package net.tutorial.springboottesting.service.impl;

import java.util.Optional;
import net.tutorial.springboottesting.exception.ResourceNotFoundException;
import net.tutorial.springboottesting.model.Employee;
import net.tutorial.springboottesting.repository.EmployeeRepository;
import net.tutorial.springboottesting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public Employee saveEmployee(Employee employee) {

    Optional<Employee> saveEmployee = employeeRepository.findByEmail(employee.getEmail());
    if (saveEmployee.isPresent()) {
      throw new ResourceNotFoundException("Employee already exist with given email: " + employee.getEmail());
    }
    return employeeRepository.save(employee);
  }
}
