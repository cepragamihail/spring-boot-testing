package net.tutorial.springboottesting.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
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
  public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
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
  public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
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

}