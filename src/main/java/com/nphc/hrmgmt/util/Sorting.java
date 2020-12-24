package com.nphc.hrmgmt.util;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.nphc.hrmgmt.model.Employee;

public interface Sorting {

	public static List<Employee>  sort(List<Employee> employees ,String filter , String sorting) {

		List<Employee> sortedList = null;

		if(filter.equalsIgnoreCase("salary")) {
			if(sorting.equalsIgnoreCase("ASC")) {
				sortedList = employees.stream()
						.sorted(Comparator.comparing(Employee::getSalary))
						.collect(Collectors.toList());

			} else {
				sortedList = employees.stream()
						.sorted(Comparator.comparing(Employee::getSalary).reversed())
						.collect(Collectors.toList());
			}
		}else if (filter.equalsIgnoreCase("name")){
			if(sorting.equalsIgnoreCase("ASC")) {
				sortedList = employees.stream()
						.sorted(Comparator.comparing(Employee::getName))
						.collect(Collectors.toList());
			} else {
				sortedList = employees.stream()
						.sorted(Comparator.comparing(Employee::getName).reversed())
						.collect(Collectors.toList());
			}
		}else if (filter.equalsIgnoreCase("login")){
			if(sorting.equalsIgnoreCase("ASC")) {
				sortedList = employees.stream()
						.sorted(Comparator.comparing(Employee::getLogin))
						.collect(Collectors.toList());
			} else {
				sortedList = employees.stream()
						.sorted(Comparator.comparing(Employee::getLogin).reversed())
						.collect(Collectors.toList());
			}

		}
		return sortedList;
	}
}