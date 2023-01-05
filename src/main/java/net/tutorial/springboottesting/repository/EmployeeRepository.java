package net.tutorial.springboottesting.repository;

import java.util.Optional;
import net.tutorial.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByEmail(String email);
}
