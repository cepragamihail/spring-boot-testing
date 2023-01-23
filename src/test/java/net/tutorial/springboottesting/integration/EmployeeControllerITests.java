package net.tutorial.springboottesting.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    response.andExpect(status().isCreated())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.email", is(employee.getEmail())));
  }

  // Integration test for getAllEmployees end point
  @DisplayName("Integration test for getAllEmployees end point")
  @Test
  public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
    // given - precondition or setup
    List<Employee> listOfEmployees = new ArrayList<>();
    listOfEmployees.add(Employee.builder().firstName("Mihail0")
        .lastName("Cepraga0").email("mcepraga0@mail.com").build());
    listOfEmployees.add(Employee.builder().firstName("Mihail1")
        .lastName("Cepraga1").email("mcepraga1@mail.com").build());
    employeeRepository.saveAll(listOfEmployees);

    // when - action or the behaviour that we are going test
    ResultActions resultResponse = mockMvc.perform(get("/api/employees"));

    // then - verify the output
    resultResponse.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
  }

  // positive scenario - void employee id
  // Integration test for GET employee by id REST API
  @DisplayName("Integration test for GET employee by id REST API positive scenario")
  @Test
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
    // given - precondition or setup
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mcepraga@mail.com")
        .build();
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

    // then - verify the output
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.email", is(employee.getEmail())));
  }

  // negative scenario - void employee id
  // Integration test for GET employee by id REST API
  @DisplayName("Integration test for GET employee by id REST API negative scenario")
  @Test
  public void givenEmployeeId_whenGetEmployeeId_thenReturnEmptyEmployeeObject() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mcepraga@mail.com")
        .build();
    employeeRepository.save(employee);

    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

    // then - verify the output
    response.andExpect(status().isNotFound())
        .andDo(print());
  }
  // Integration test for update employee REST API - positive scenario
  @DisplayName("Integration test for update employee REST API - positive scenario")
  @Test
  public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnEmployeeObject() throws Exception {
    // given - precondition or setup
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mcepraga@mail.com")
        .build();
    employeeRepository.save(employee);

    Employee updatedEmployee = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mcepraga1@mail.com")
        .build();


    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(put("/api/employees/{id}", employee.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedEmployee))
    );
    // then - verify the output
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
        .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
  }

}
