package net.tutorial.springboottesting.service;

import java.util.Optional;
import net.tutorial.springboottesting.model.Employee;
import net.tutorial.springboottesting.repository.EmployeeRepository;
import net.tutorial.springboottesting.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class EmployeeServiceTests {

  private EmployeeRepository employeeRepository;
  private EmployeeService employeeService;

  @BeforeEach
  public void setup() {
    employeeRepository = Mockito.mock(EmployeeRepository.class);
    employeeService = new EmployeeServiceImpl(employeeRepository);
  }

  // JUnit test for
  @Test
  public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
    // given - precondition or setup
    Employee employee = Employee.builder()
        .id(1L)
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mihail@mail.com")
        .build();
    BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
        .willReturn(Optional.empty());
    BDDMockito.given(employeeRepository.save(employee))
        .willReturn(employee);

    // when - action or the behaviour that we are going test
    Employee saveEmployee = employeeService.saveEmployee(employee);

    // then - verify the output
    Assertions.assertThat(saveEmployee).isNotNull();
    Assertions.assertThat(saveEmployee.getId()).isEqualTo(employee.getId());

  }


}
