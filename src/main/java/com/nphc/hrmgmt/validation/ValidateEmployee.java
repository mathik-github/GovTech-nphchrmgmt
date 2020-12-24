package com.nphc.hrmgmt.validation;

import java.math.BigDecimal;

import org.apache.commons.validator.GenericValidator;

import com.nphc.hrmgmt.dto.EmployeeRequestDto;
import com.nphc.hrmgmt.service.EmployeeService;

public interface ValidateEmployee {

	public static final EmployeeService employeeService = new EmployeeService();

	public static String validate(EmployeeRequestDto employeeDto) {

		StringBuffer  respMessage = new StringBuffer();

		if(null != employeeDto ) {

			if(employeeDto.getSalary().compareTo(BigDecimal.ZERO) <= 0 ) {
			    System.out.println("Invalid salary of " + employeeDto.getId());
				respMessage.append("Invalid salary of employee id  ")
				           .append(employeeDto.getId())
				           .append(" , ");
			} 
			if(null != employeeDto.getStartDate()) {
				if(!(GenericValidator.isDate(employeeDto.getStartDate(), "yyyy-MM-dd", true) ||
						GenericValidator.isDate(employeeDto.getStartDate(), "dd-MMM-yy", true))) {
					
					respMessage.append("The date is not valid format of employee id  ")
			           .append(employeeDto.getId())
			           .append(" , ");
			        

				}
			}
		}
		return respMessage.toString();
	}

}
