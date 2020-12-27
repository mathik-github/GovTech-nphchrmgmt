package com.nphc.hrmgmt.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nphc.hrmgmt.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> ,PagingAndSortingRepository<Employee, String>{

	/**
	 * 
	 * @param minSalary
	 * @param maxSalary
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Query(value="SELECT *FROM Employee  WHERE salary >= ?1 and salary < ?2 ORDER BY id", nativeQuery = true)
	public List<Employee> searchEmployees(BigDecimal minSalary,BigDecimal maxSalary);

	@Query(value="SELECT *FROM Employee  WHERE salary >= ?1 and salary < ?2 ORDER BY id limit ?3 ,?4", nativeQuery = true)
	public List<Employee> searchEmployeesWithLimit(BigDecimal minSalary,BigDecimal maxSalary,  int offset, int limit);

	/**
	 * 
	 * @param login
	 * @return
	 */
	@Query(value="SELECT e.login FROM Employee e  WHERE e.login = ?1 ", nativeQuery = true)
	public String isLoginExist(String login);

}
