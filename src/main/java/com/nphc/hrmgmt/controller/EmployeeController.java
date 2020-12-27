package com.nphc.hrmgmt.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.hrmgmt.dto.EmpSearchRequestDto;
import com.nphc.hrmgmt.dto.EmployeeRequestDto;
import com.nphc.hrmgmt.helper.CSVHelper;
import com.nphc.hrmgmt.message.ResponseMessage;
import com.nphc.hrmgmt.model.Employee;
import com.nphc.hrmgmt.service.EmployeeService;
import com.nphc.hrmgmt.util.DateConvertor;
import com.nphc.hrmgmt.validation.ValidateEmployee;

/**
 * 
 * @author Mathi
 * 
 * TODO - All message will be moved to properties file and read it from there and use it instead of hard coded 
 *
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	/**
	 * To upload the csv file 
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/users/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

		String responseMsg = "";

		if (CSVHelper.hasCSVFormat(file)) {

			try {

				responseMsg = employeeService.save(file);	

			} catch (Exception e) {

				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
			}
		}else {
			responseMsg = "Invalid csv file!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(responseMsg,HttpStatus.BAD_REQUEST.value()));
		}


		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(responseMsg,HttpStatus.OK.value()));
	}

	/**
	 *  Fetch users based on the salary range
	 *  
	 * @param searchRequest
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Map<String, List<Employee>> fetchEmployees(@RequestBody EmpSearchRequestDto searchRequest) {

		Map<String, List<Employee>> results = new HashMap<String, List<Employee>>();

		List<Employee> employees = employeeService.fetchEmployees(searchRequest);

		results.put("results", employees);

		return results;
	}


	/**
	 * To fetch the particular employee
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public Employee getEmplyee(@PathVariable("id") String id) {
		return employeeService.getEmployee(id);
	}

	/**
	 *  To create the employee
	 * @param employeeDto
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> saveEmplyee(@RequestBody EmployeeRequestDto employeeDto) {

		boolean isLoginExists = false;

		String message ="";

		Employee employee = employeeService.getEmployee(employeeDto.getId());

		isLoginExists = employeeService.isLoginExists(employeeDto.getLogin());

		if(null != employee) {
			message = "Employee ID already exists";
		} else if (isLoginExists){
			message = "Employee login not unique";
		}else {

			message = ValidateEmployee.validate(employeeDto);
		}

		if (null != message && !message.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,HttpStatus.BAD_REQUEST.value()));

		} else {

			employee = this.populateData(employeeDto);

			employeeService.saveEmployee(employee);

			message = "Successfully created";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,HttpStatus.OK.value()));
		}


	}
	/**
	 * To update the employee
	 * 
	 * @param employeeDto
	 * @return
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateEmployee(@RequestBody EmployeeRequestDto employeeDto) {

		boolean isLoginExists = false ;
		String message ="";

		Employee employee = employeeService.getEmployee(employeeDto.getId());

		if(null != employee) {
			if(!employee.getLogin().equalsIgnoreCase(employeeDto.getLogin())) {
				isLoginExists = employeeService.isLoginExists(employeeDto.getLogin());
			}
		}

		/**
		 *  All message read from properties file
		 */
		if(null == employee) {
			message = "No such employee";
		}else if(isLoginExists){
			message = "Employee login not unique";
		}else {
			message = ValidateEmployee.validate(employeeDto);
		}

		if (null != message && !message.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,HttpStatus.BAD_REQUEST.value()));

		} else {

			employee = this.populateData(employeeDto);

			employeeService.saveEmployee(employee);

			message = "Successfully Updated";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,HttpStatus.OK.value()));
		}
	}

	/**
	 * To delete the employee  . As of now it is hard deleted instead of soft delete 
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deleteEmployee(@PathVariable("id") String id) {

		String message = "";

		Employee employee = employeeService.getEmployee(id);

		if(null == employee ) {
			message = "No such employee";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,HttpStatus.BAD_REQUEST.value()));
		}else {
			employeeService.deleteEmployee(id);
			message = "Successfully deleted";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,HttpStatus.OK.value()));
		}


	}

	private Employee populateData(EmployeeRequestDto employeeRequestDto) {

		Employee employee = new Employee();

		if (null != employeeRequestDto.getId() && employeeRequestDto.getId().trim().length()!=0) {
			employee.setId(employeeRequestDto.getId());
		}
		if (null != employeeRequestDto.getLogin() && employeeRequestDto.getLogin().trim().length()!=0) {
			employee.setLogin(employeeRequestDto.getLogin());
		}

		if (null != employeeRequestDto.getName() && employeeRequestDto.getName().trim().length()!=0) {
			employee.setName(employeeRequestDto.getName());
		}
		if (null != employeeRequestDto.getSalary() && employeeRequestDto.getSalary().compareTo(BigDecimal.ZERO) < 0 ) {
			employee.setSalary(employeeRequestDto.getSalary());
		}
		if (null != employeeRequestDto.getStartDate() && employeeRequestDto.getStartDate().trim().length()!=0) {

			employee.setStartDate(Date.valueOf(DateConvertor.dateConvertor(employeeRequestDto.getStartDate())));
		}
		return employee;
	}



}
