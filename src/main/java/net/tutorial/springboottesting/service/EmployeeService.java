package net.tutorial.springboottesting.service;

import java.util.List;
import java.util.Optional;
import net.tutorial.springboottesting.model.Employee;

public interface EmployeeService {

  Employee saveEmployee(Employee employee);
  List<Employee> getAllEmployees();
  Optional<Employee> getEmployeeById(long id);
  Employee updateEmployee(Employee updateEmployee);

}
