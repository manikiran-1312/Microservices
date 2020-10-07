package com.example.demo.Dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

	Page<Employee> findByNameContaining(String search, org.springframework.data.domain.Pageable paging);

	Page<Employee> findBySal(Integer search, Pageable paging);

	Page<Employee> findByCityContaining(String search, Pageable paging);

	Optional<Employee> findByPhone(int phone);

}
