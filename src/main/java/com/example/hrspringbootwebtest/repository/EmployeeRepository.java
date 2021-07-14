package com.example.hrspringbootwebtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrspringbootwebtest.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}
