package net.tutorial.springboottesting.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import net.tutorial.springboottesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryITests {

  @Autowired
  private EmployeeRepository employeeRepository;

  private Employee employee;

  @BeforeEach
  public void setup() {
    employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mihail-cepraga@mail.net")
        .build();
  }

  // Integration test for save employee operation
  @DisplayName("Integration test for save employee operation")
  @Test
  public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

    // when - action or the behavior that we are going to test
    Employee savedEmployee = employeeRepository.save(employee);

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isGreaterThan(0);
  }

  // Integration test for get all employees operation
  @DisplayName("Integration test for get all employees operation")
  @Test
  public void givenEmployeeList_whenFindAll_thenEmployeeList() {
    // given - precondition or setup
    Employee employee2 = Employee.builder()
        .firstName("Mihail2")
        .lastName("Cepraga2")
        .email("mihail-cepraga2@mail.net")
        .build();

    employeeRepository.save(employee);
    employeeRepository.save(employee2);

    // when - action or the behaviour that we are going test
    List<Employee> employeeList = employeeRepository.findAll();

    // then - verify the output
    assertThat(employeeList).isNotNull();
    assertThat(employeeList.size()).isEqualTo(2);

  }

  // Integration test for get employee by id operation
  @DisplayName("Integration test for get employee by id operation")
  @Test
  public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
    // given - precondition or setup
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    Employee employeeDB = employeeRepository.findById(employee.getId()).get();

    // then - verify the output
    assertThat(employeeDB).isNotNull();
  }

  // Integration test for get employee by email operation
  @DisplayName("Integration test for get employee by email operation")
  @Test
  public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
    // given - precondition or setup
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

    // then - verify the output
    assertThat(employeeDB).isNotNull();
    assertThat(employeeDB.getEmail()).isEqualTo(employee.getEmail());
  }

  // Integration test for update employee operation
  @DisplayName("Integration test for update employee operation")
  @Test
  public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
    // given - precondition or setup
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
    savedEmployee.setEmail("mihail-cepraga@mail.com");
    savedEmployee.setFirstName("Mihail");
    Employee updatedEmployee = employeeRepository.save(savedEmployee);

    // then - verify the output
    assertThat(updatedEmployee.getEmail()).isEqualTo("mihail-cepraga@mail.com");
    assertThat(updatedEmployee.getFirstName()).isEqualTo("Mihail");
  }

  // Integration test for delete employee operation
  @DisplayName("Integration test for delete employee operation")
  @Test
  public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
    // given - precondition or setup
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    employeeRepository.delete(employee);
    Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
    // then - verify the output
    assertThat(employeeOptional).isEmpty();
  }

  // Integration test for custom query using JPQL with index
  @DisplayName("Integration test for custom query using JPQL with index")
  @Test
  public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
    // given - precondition or setup
    employeeRepository.save(employee);
    final String firstName = "Mihail";
    final String lastName = "Cepraga";

    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getFirstName()).isEqualTo(firstName);
    assertThat(savedEmployee.getLastName()).isEqualTo(lastName);
  }

  // Integration test for custom query using JPQL with named params
  @DisplayName("Integration test for custom query using JPQL with named params")
  @Test
  public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
    // given - precondition or setup
    employeeRepository.save(employee);
    final String firstName = "Mihail";
    final String lastName = "Cepraga";

    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getFirstName()).isEqualTo(firstName);
    assertThat(savedEmployee.getLastName()).isEqualTo(lastName);
  }

  // Integration test for custom query using native SQL with index
  @DisplayName("Integration test for custom query using native SQL with index")
  @Test
  public void givenFirstNameAndLastName_whenFindNativeSQL_thenReturnEmployeeObject() {
    // given - precondition or setup
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    Employee saveEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

    // then - verify the output
    assertThat(saveEmployee).isNotNull();
  }

  // Integration test for custom query using native SQL with named params
  @DisplayName("Integration test for custom query using native SQL with named params")
  @Test
  public void givenFirstNameAndLastName_whenFindNativeSQLNamed_thenReturnEmployeeObject() {
    // given - precondition or setup
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    Employee saveEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

    // then - verify the output
    assertThat(saveEmployee).isNotNull();
  }
}
