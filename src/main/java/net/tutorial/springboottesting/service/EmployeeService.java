package net.tutorial.springboottesting.service;

import java.util.List;
import net.tutorial.springboottesting.model.Employee;

public interface EmployeeService {

  Employee saveEmployee(Employee employee);
  List<Employee> getAllEmployees();

}
