package com.nphc.hrmgmt.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.hrmgmt.dto.EmployeeRequestDto;
import com.nphc.hrmgmt.model.Employee;
import com.nphc.hrmgmt.validation.ValidateEmployee;

public class CSVHelper {

	public static String TYPE = "text/csv";

	static String[] HEADERs = { "Id", "login", "name", "salary","startDate" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}


	public static List<EmployeeRequestDto> readCSVFile(InputStream is) throws Exception{

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser  csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {


			EmployeeRequestDto employeeRequestDto = null;

			List<EmployeeRequestDto> employees = new ArrayList<EmployeeRequestDto>();


			Iterable<CSVRecord> csvRecords = csvParser.getRecords();


			for (CSVRecord csvRecord : csvRecords) {

				employeeRequestDto = new EmployeeRequestDto();

				if(null != csvRecord.get("Id") && !csvRecord.get("Id").startsWith("#") ) {

					if(null != csvRecord.get("Id")) {
						employeeRequestDto.setId(csvRecord.get("Id"));
					}
					if(null != csvRecord.get("login")) {
						employeeRequestDto.setLogin(csvRecord.get("login"));	
					}

					if(null != csvRecord.get("name")) {
						employeeRequestDto.setName(csvRecord.get("name"));
					}
					if(null != csvRecord.get("salary")) {
						employeeRequestDto.setSalary(new BigDecimal(csvRecord.get("salary")));
					}
					if(null != csvRecord.get("startDate")) {
						employeeRequestDto.setStartDate(csvRecord.get("startDate"));
					}

					employees.add(employeeRequestDto);
				

				}


			}

			return employees;

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}
}
