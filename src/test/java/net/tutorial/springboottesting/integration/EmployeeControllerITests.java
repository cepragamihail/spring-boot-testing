package net.tutorial.springboottesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.tutorial.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    employeeRepository.deleteAll();
  }

}
