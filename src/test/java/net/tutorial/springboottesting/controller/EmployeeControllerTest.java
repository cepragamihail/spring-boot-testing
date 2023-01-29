package net.tutorial.springboottesting.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import net.tutorial.springboottesting.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private EmployeeService employeeService;

  @Autowired
  private ObjectMapper objectMapper;

  // JUnit test for createEmployee end point
  @DisplayName("JUnit test for createEmployee end point")
  @Test
  void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
    // given - precondition or setup
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mcepraga@mail.com")
        .build();
    given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
        .willAnswer((invocation) -> invocation.getArgument(0));

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

  // JUnit test for getAllEmployees end point
  @DisplayName("JUnit test for getAllEmployees end point")
  @Test
  void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
    // given - precondition or setup
    List<Employee> listOfEmployees = new ArrayList<>();
    listOfEmployees.add(Employee.builder().firstName("Mihail0")
        .lastName("Cepraga0").email("mcepraga0@mail.com").build());
    listOfEmployees.add(Employee.builder().firstName("Mihail1")
        .lastName("Cepraga1").email("mcepraga1@mail.com").build());
    given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

    // when - action or the behaviour that we are going test
    ResultActions resultResponse = mockMvc.perform(get("/api/employees"));

    // then - verify the output
    resultResponse.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
  }

  // positive scenario - void employee id
  // JUnit test for GET employee by id REST API
  @DisplayName("JUnit test for GET employee by id REST API positive scenario")
  @Test
  void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mcepraga@mail.com")
        .build();
    given(employeeService.getEmployeeById(employeeId))
        .willReturn(Optional.of(employee));

    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

    // then - verify the output
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.email", is(employee.getEmail())));
  }

  // negative scenario - void employee id
  // JUnit test for GET employee by id REST API
  @DisplayName("JUnit test for GET employee by id REST API negative scenario")
  @Test
  void givenEmployeeId_whenGetEmployeeId_thenReturnEmptyEmployeeObject() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    given(employeeService.getEmployeeById(employeeId))
        .willReturn(Optional.empty());

    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

    // then - verify the output
    response.andExpect(status().isNotFound())
        .andDo(print());
  }

  // JUnit test for update employee REST API - positive scenario
  @DisplayName("JUnit test for update employee REST API - positive scenario")
  @Test
  void givenUpdatedEmployee_whenUpdateEmployee_thenReturnEmployeeObject() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    Employee employee = Employee.builder()
        .firstName("Mihail")
        .lastName("Cepraga")
        .email("mcepraga@mail.com")
        .build();

    Employee updatedEmployee = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mcepraga1@mail.com")
        .build();
    given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
    given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));

    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
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

  // JUnit test for update employee REST API - negative scenario
  @DisplayName("JUnit test for update employee REST API - negative scenario")
  @Test
  void givenUpdatedEmployee_whenUpdateEmployee_thenReturnNOT_FOUND() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    Employee updatedEmployee = Employee.builder()
        .firstName("Mihail1")
        .lastName("Cepraga1")
        .email("mcepraga1@mail.com")
        .build();
    given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
    given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));

    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedEmployee))
    );
    // then - verify the output
    response.andExpect(status().isNotFound())
        .andDo(print());
  }

  // JUnit test for delete employee REST API
  @DisplayName("JUnit test for delete employee REST API")
  @Test
  void givenEmployeeId_whenDeleteEmployee_thenReturnOK() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    willDoNothing().given(employeeService).deleteEmployeeById(employeeId);

    // when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));

    // then - verify the output
    response.andExpect(status().isOk())
        .andDo(print());
  }
}