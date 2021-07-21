package com.example.hrspringbootwebtest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.example.hrspringbootwebtest.model.Employee;
import com.example.hrspringbootwebtest.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class TestEmployeeRESTController {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	public void getContext() {
		mvc = webAppContextSetup(webApplicationContext).build();
		
		employeeRepository.save(new Employee(2l, "aaa2", "bbb2", "ccc2"));
		employeeRepository.save(new Employee(3l, "aaa2", "bbb2", "ccc2"));
	}

	@Test
	public void getAllEmployeesAPI() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees")
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void createEmployeeAPI() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/employees")
				.content(asJsonString(new Employee(1l, "firstName4", "lastName4", "email4@mail.com")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());
		
		mvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(1));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void updateEmployeeAPI() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.put("/employees/{id}", 2)
				.content(asJsonString(new Employee(2l, "firstName2", "lastName2", "email2@mail.com")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstName2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email2@mail.com"));
	}

	@Test
	public void deleteEmployeeAPI() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", 3)).andExpect(status().isAccepted());
	}

}
