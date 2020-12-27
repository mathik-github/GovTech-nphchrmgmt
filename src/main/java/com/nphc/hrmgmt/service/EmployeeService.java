package com.nphc.hrmgmt.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.hrmgmt.dto.EmpSearchRequestDto;
import com.nphc.hrmgmt.dto.EmployeeRequestDto;
import com.nphc.hrmgmt.helper.ApplicationProperties;
import com.nphc.hrmgmt.helper.CSVHelper;
import com.nphc.hrmgmt.model.Employee;
import com.nphc.hrmgmt.repository.EmployeeRepository;
import com.nphc.hrmgmt.util.DateConvertor;
import com.nphc.hrmgmt.util.Sorting;
import com.nphc.hrmgmt.validation.ValidateEmployee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ApplicationProperties properties;

	/**
	 * To  upload the file into DB
	 * @param file
	 * @throws Exception
	 */

	public String save(MultipartFile file) throws Exception {

		try {

			List<EmployeeRequestDto> employeeDtos = CSVHelper.readCSVFile(file.getInputStream());

			Employee employee =null;

			List<Employee> employees =  null ;

			List<String> errorsList = new ArrayList<String>();

			StringBuffer errors = new StringBuffer();

			if(null != employeeDtos && !employeeDtos.isEmpty()) {

				employees =  new ArrayList<Employee>();

				for(EmployeeRequestDto employeeRequestDto :employeeDtos) {

					StringBuffer errorsMsg = new StringBuffer();

					employee = this.getEmployee(employeeRequestDto.getId());

					/*
					 *  Validate the login is unique or not
					 */
					if(null != employee && employee.getId().trim().length() != 0) {
						if(!employee.getLogin().equalsIgnoreCase(employeeRequestDto.getLogin())) {
							if(this.isLoginExists(employeeRequestDto.getLogin())) {
								errorsMsg.append("Employee login not unique of employee id ")
								.append(employeeRequestDto.getId())
								.append(" , ");

							}
						}
					}

					/**
					 *  Validate the salary and Date 
					 */
					String isValidEmp =  ValidateEmployee.validate(employeeRequestDto);

					errorsMsg.append(isValidEmp);


					if(null != errorsMsg && errorsMsg.length()==0) {

						employee = new Employee();

						employee.setId(employeeRequestDto.getId());
						employee.setLogin(employeeRequestDto.getLogin());
						employee.setName(employeeRequestDto.getName());
						employee.setSalary(employeeRequestDto.getSalary());
						employee.setStartDate(Date.valueOf(DateConvertor.dateConvertor(employeeRequestDto.getStartDate())));

						employees.add(employee);

					}
					errorsList.add(errorsMsg.toString());			

				}


				employeeRepository.saveAll(employees);
			}	

			errorsList.forEach(error->errors.append(error));

			return errors.toString();

		}catch(IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	/**
	 * To fetch the employees and sort it based on the criteria -- Story 2
	 * 
	 * @param empSearchRequestDto
	 * @return
	 */
	public List<Employee> fetchEmployees(EmpSearchRequestDto empSearchRequestDto) {

		List<Employee> sortedList = null;

		List<Employee> employees = new ArrayList<Employee>();


		if(null != empSearchRequestDto) {

			if(empSearchRequestDto.getMaxSalary().compareTo(BigDecimal.ZERO) == 0) {
				empSearchRequestDto.setMaxSalary(new BigDecimal(4000));
			} 

			if(empSearchRequestDto.getMinSalary().compareTo(empSearchRequestDto.getMaxSalary())==1) {
				throw new RuntimeException("Invalid Search Criteria . Max salary should be  greater than Min Salary ");
			}else if(empSearchRequestDto.getStartingOffset()==0 || empSearchRequestDto.getLimit() == 0) {

				employees = employeeRepository.searchEmployees(empSearchRequestDto.getMinSalary(),
						empSearchRequestDto.getMaxSalary());

			}else if (empSearchRequestDto.getLimit()>0) {

				employees = employeeRepository.searchEmployeesWithLimit(empSearchRequestDto.getMinSalary(),
						empSearchRequestDto.getMaxSalary(),
						empSearchRequestDto.getStartingOffset(),
						empSearchRequestDto.getLimit());

			}
		}


		/**
		 *   Additional filter and sorting 
		 */
		if(null != empSearchRequestDto.getFilter() && empSearchRequestDto.getFilter().trim().length()!=0) {
			sortedList = Sorting.sort(employees, empSearchRequestDto.getFilter(), empSearchRequestDto.getSorting());  

		}else {
			//default sorted by  id field
			sortedList = employees;
		}

		return sortedList;

	}

	/**
	 * Get the employee details -- Story 3
	 * @param id
	 * @return
	 */

	public Employee getEmployee(String id) {

		Employee employee=null;

		Optional<Employee> optional = employeeRepository.findById(id);

		if(optional.isPresent()) {
			employee = employeeRepository.findById(id).get();
		}
		return  employee;
	}

	/**
	 * Create / Update  employee -- story 3
	 * @param employee
	 * @return
	 */

	public Employee saveEmployee(Employee employee) {

		return employeeRepository.save(employee);
	}

	/**
	 * Delete the employee -- story 3
	 * @param id
	 */

	public void deleteEmployee(String id) {

		employeeRepository.deleteById(id);

	}

	/**
	 * To check whether the particular login exist in DB or not 
	 * @param loginId
	 * @return
	 */

	public boolean isLoginExists(String loginId) {

		String login = employeeRepository.isLoginExist(loginId);

		boolean isLoginExists = false ;

		if(null != login && !login.isBlank()) {
			isLoginExists = true; 
		}
		return isLoginExists;
	}


}
