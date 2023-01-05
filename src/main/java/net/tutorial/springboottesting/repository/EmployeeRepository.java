package net.tutorial.springboottesting.repository;

import java.util.Optional;
import net.tutorial.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByEmail(String email);

  // define custom query using JPQL with index params
  @Query("select employee from Employee employee where employee.firstName = ?1 and employee.lastName = ?2")
  Employee findByJPQL(String firstName, String lastName);
}
