package com.nphc.hrmgmt.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "login")
	private String login;

	@Column(name = "name")
	private String name;

	@Column(name = "salary")
	private BigDecimal salary;

	@Column(name = "startDate")
	private Date startDate;

	public Employee(String id, String login, String name, BigDecimal salary, Date startDate) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
	}

	public Employee() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", login=" + login + ", name=" + name + ", salary=" + salary + ", startDate="
				+ startDate + "]";
	}

}
