package net.tutorial.springboottesting.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.tutorial.springboottesting.exception.ResourceNotFoundException;
import net.tutorial.springboottesting.model.Employee;
import net.tutorial.springboottesting.repository.EmployeeRepository;
import net.tutorial.springboottesting.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

  @Mock
  private EmployeeRepository employeeRepository;
  @InjectMocks
  private EmployeeServiceImpl employeeService;

  Employee employee;

  @BeforeEach
  public void setup() {
    employee = Employee.builder().id(1L).firstName("Mihail").lastName("Cepraga").email("mihail@mail.com").build();
  }

  // JUnit test for saveEmployee method
  @DisplayName("JUnit test for saveEmployee method")
  @Test
  public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
    // given - precondition or setup
    given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
    given(employeeRepository.save(employee)).willReturn(employee);

    // when - action or the behaviour that we are going test
    Employee saveEmployee = employeeService.saveEmployee(employee);

    // then - verify the output
    assertThat(saveEmployee).isNotNull();
    assertThat(saveEmployee.getId()).isEqualTo(employee.getId());
  }

  // JUnit test for saveEmployee method which throws exception
  @DisplayName("JUnit test for saveEmployee method which throws exception")
  @Test
  public void givenEmployeeObject_whenSaveEmployee_thenThrowsException() {
    // given - precondition or setup
    given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

    // when - action or the behaviour that we are going test
    org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      employeeService.saveEmployee(employee);
    });

    // then - verify the output
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  // JUnit test for getAllEmployees method
  @DisplayName("JUnit test for getAllEmployees method")
  @Test
  public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList() {
    // given - precondition or setup
    Employee employee1 = Employee.builder().id(1L).firstName("Mihail1").lastName("Cepraga1").email("mihail1@mail.com").build();
    given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

    // when - action or the behaviour that we are going test
    List<Employee> employeeList = employeeService.getAllEmployees();

    // then - verify the output
    assertThat(employeeList).isNotNull();
    assertThat(employeeList.size()).isEqualTo(2);
  }

  // JUnit test for getAllEmployees method (negative scenario)
  @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
  @Test
  public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
    // given - precondition or setup
    given(employeeRepository.findAll()).willReturn(Collections.EMPTY_LIST);

    // when - action or the behaviour that we are going test
    List<Employee> employeeList = employeeService.getAllEmployees();

    // then - verify the output
    assertThat(employeeList).isEmpty();
    assertThat(employeeList.size()).isEqualTo(0);
  }

  // JUnit test for getEmployeeById method
  @DisplayName("JUnit test for getEmployeeById method")
  @Test
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
    // given - precondition or setup
    given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isEqualTo(1L);
  }

  // JUnit test for updateEmployee method
  @DisplayName("JUnit test for updateEmployee method")
  @Test
  public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
    // given - precondition or setup
    given(employeeRepository.save(employee)).willReturn(employee);
    employee.setFirstName("UpdatedName");
    employee.setEmail("updatedmail@mail.com");
    // when - action or the behaviour that we are going test
    Employee updatedEmployee = employeeService.updateEmployee(employee);

    // then - verify the output
    assertThat(updatedEmployee).isNotNull();
    assertThat(updatedEmployee.getFirstName()).isEqualTo("UpdatedName");
    assertThat(updatedEmployee.getEmail()).isEqualTo("updatedmail@mail.com");
  }

}
