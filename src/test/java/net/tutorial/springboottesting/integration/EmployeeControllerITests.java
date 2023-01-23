package net.tutorial.springboottesting.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.tutorial.springboottesting.model.Employee;
import net.tutorial.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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

  // Integration test for createEmployee end point
  @DisplayName("Integration test for createEmployee end point")
  @Test
  public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
    // given - precondition or setup
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mcepraga@mail.com")
        .build();

    // when - action or behaviour that we are going test
    ResultActions response = mockMvc.perform(
        post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee))
    );

    // then - verify the result or output using assert statements
    response.andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.email", is(employee.getEmail())));
  }

}
