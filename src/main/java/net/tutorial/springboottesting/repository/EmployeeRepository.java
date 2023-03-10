package net.tutorial.springboottesting.repository;

import java.util.Optional;
import net.tutorial.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByEmail(String email);

  // define custom query using JPQL with index params
  @Query("select employee from Employee employee where employee.firstName = ?1 and employee.lastName = ?2")
  Employee findByJPQL(String firstName, String lastName);

  // define custom query using JPQL with named params
  @Query("select employee from Employee employee where employee.firstName =:firstName and employee.lastName =:lastName")
  Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

  // define custom query using native SQL with index params
  @Query(value = "select * from employees e where e.first_name=?1 and e.last_name=?2", nativeQuery = true)
  Employee findByNativeSQL(String firstName, String lastName);

  // define custom query using native SQL with index params
  @Query(value = "select * from employees e where e.first_name=:firstName and e.last_name=:lastName", nativeQuery = true)
  Employee findByNativeSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
