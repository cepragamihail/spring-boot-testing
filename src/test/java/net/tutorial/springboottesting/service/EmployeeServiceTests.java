package net.tutorial.springboottesting.service;

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
    Assertions.assertThat(saveEmployee).isNotNull();
    Assertions.assertThat(saveEmployee.getId()).isEqualTo(employee.getId());
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


}
