package com.nphc.hrmgmt.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.nphc.hrmgmt.service.EmployeeService;

public interface DateConvertor {

	public static String dateConvertor(String date) {

		DateTimeFormatter dateFormatter = null;
		/*
		 *  TODO Date type pattern like yyyy-MM-dd will be stored in DB and retried for extended more types
		 *  as a config
		 *  
		 */
		if(GenericValidator.isDate(date, "yyyy-MM-dd", true)) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		}else if(GenericValidator.isDate(date, "dd-MMM-yy", true)) {
			dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
		}
		LocalDate localDate = LocalDate.parse(date, dateFormatter);

		return localDate.toString();

	}

}
