package net.tutorial.springboottesting.repository;

import java.util.List;
import net.tutorial.springboottesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EmployeeRepositoryTests {

  @Autowired
  private EmployeeRepository employeeRepository;

  // JUnit test for save employee operation
  @DisplayName("JUnit test for save employee operation")
  @Test
  public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
    // given - precondition or setup
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mihail-cepraga@mail.net")
        .build();

    // when - action or the behavior that we are going to test
    Employee savedEmployee = employeeRepository.save(employee);

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isGreaterThan(0);
  }

    // JUnit test for get all employees operation
      @DisplayName("JUnit test for get all employees operation")
      @Test
      public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
            .firstName("Mihail1")
            .lastName("Cepraga1")
            .email("mihail-cepraga1@mail.net")
            .build();
        Employee employee2 = Employee.builder()
            .firstName("Mihail2")
            .lastName("Cepraga2")
            .email("mihail-cepraga2@mail.net")
            .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

      }

}
