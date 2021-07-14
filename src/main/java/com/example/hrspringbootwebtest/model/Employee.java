package com.example.hrspringbootwebtest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Employee {
	public Employee(Long id, String firstName, String lastName, String emailId) {
		// TODO Auto-generated constructor stub
		this.employeeId = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = emailId;
	}
	
	public Employee() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long employeeId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
}
