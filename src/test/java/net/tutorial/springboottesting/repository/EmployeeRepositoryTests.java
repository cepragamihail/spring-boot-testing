package net.tutorial.springboottesting.repository;

import java.util.List;
import java.util.Optional;
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

  // JUnit test for get employee by id operation
  @DisplayName("JUnit test for get employee by id operation")
  @Test
  public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);

    // when - action or the behaviour that we are going test
    Employee employeeDB = employeeRepository.findById(employee1.getId()).get();

    // then - verify the output
    assertThat(employeeDB).isNotNull();
  }

  // JUnit test for get employee by email operation
  @DisplayName("JUnit test for get employee by email operation")
  @Test
  public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);

    // when - action or the behaviour that we are going test
    Employee employeeDB = employeeRepository.findByEmail(employee1.getEmail()).get();

    // then - verify the output
    assertThat(employeeDB).isNotNull();
    assertThat(employeeDB.getEmail()).isEqualTo(employee1.getEmail());
  }

  // JUnit test for update employee operation
  @DisplayName("JUnit test for update employee operation")
  @Test
  public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);

    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.findById(employee1.getId()).get();
    savedEmployee.setEmail("mihail-cepraga@mail.com");
    savedEmployee.setFirstName("Mihail");
    Employee updatedEmployee = employeeRepository.save(savedEmployee);

    // then - verify the output
    assertThat(updatedEmployee.getEmail()).isEqualTo("mihail-cepraga@mail.com");
    assertThat(updatedEmployee.getFirstName()).isEqualTo("Mihail");
  }

  // JUnit test for delete employee operation
  @DisplayName("JUnit test for delete employee operation")
  @Test
  public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);

    // when - action or the behaviour that we are going test
    employeeRepository.delete(employee1);
    Optional<Employee> employeeOptional = employeeRepository.findById(employee1.getId());
    // then - verify the output
    assertThat(employeeOptional).isEmpty();
  }

  // JUnit test for custom query using JPQL with index
  @DisplayName("JUnit test for custom query using JPQL with index")
  @Test
  public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);
    String firstName = "Mihail1";
    String lastName = "Cepraga1";

    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getFirstName()).isEqualTo(firstName);
    assertThat(savedEmployee.getLastName()).isEqualTo(lastName);
  }

  // JUnit test for custom query using JPQL with named params
  @DisplayName("JUnit test for custom query using JPQL with named params")
  @Test
  public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);
    String firstName = "Mihail1";
    String lastName = "Cepraga1";

    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getFirstName()).isEqualTo(firstName);
    assertThat(savedEmployee.getLastName()).isEqualTo(lastName);
  }

  // JUnit test for custom query using native SQL with index
  @DisplayName("JUnit test for custom query using native SQL with index")
  @Test
  public void givenFirstNameAndLastName_whenFindNativeSQL_thenReturnEmployeeObject() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);

    // when - action or the behaviour that we are going test
    Employee saveEmployee = employeeRepository.findByNativeSQL(employee1.getFirstName(), employee1.getLastName());

    // then - verify the output
    assertThat(saveEmployee).isNotNull();
  }

  // JUnit test for custom query using native SQL with named params
  @DisplayName("JUnit test for custom query using native SQL with named params")
  @Test
  public void givenFirstNameAndLastName_whenFindNativeSQLNamed_thenReturnEmployeeObject() {
    // given - precondition or setup
    Employee employee1 = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mihail-cepraga1@mail.net")
        .build();
    employeeRepository.save(employee1);

    // when - action or the behaviour that we are going test
    Employee saveEmployee = employeeRepository.findByNativeSQLNamed(employee1.getFirstName(), employee1.getLastName());

    // then - verify the output
    assertThat(saveEmployee).isNotNull();
  }
}
