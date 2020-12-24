package com.nphc.hrmgmt.dto;

import java.math.BigDecimal;

public class EmployeeRequestDto {
	
	private String id;
	private String name;
	private String login;
	private BigDecimal salary;
	private String startDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Override
	public String toString() {
		return "EmployeeRequestDto [id=" + id + ", name=" + name + ", login=" + login + ", salary=" + salary
				+ ", startDate=" + startDate + "]";
	}
	

}
