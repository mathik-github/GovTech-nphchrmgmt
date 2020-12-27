package com.nphc.hrmgmt.dto;

import java.math.BigDecimal;

public class EmpSearchRequestDto {

	private BigDecimal minSalary;
	private BigDecimal maxSalary;
	private int startingOffset;
	private int limit;
	private String filter;
	private String sorting;

	public EmpSearchRequestDto() {
		minSalary = new BigDecimal(0);
		maxSalary = new BigDecimal(0);
		filter = "id";
		sorting = "ASC";
	}

	public BigDecimal getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(BigDecimal minSalary) {
		this.minSalary = minSalary;
	}
	public BigDecimal getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(BigDecimal maxSalary) {
		this.maxSalary = maxSalary;
	}
	public int getStartingOffset() {
		return startingOffset;
	}
	public void setStartingOffset(int startingOffset) {
		this.startingOffset = startingOffset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getSorting() {
		return sorting;
	}
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	@Override
	public String toString() {
		return "EmpSearchRequestDto [minSalary=" + minSalary + ", maxSalary=" + maxSalary + ", startingOffset="
				+ startingOffset + ", limit=" + limit + ", filter=" + filter + ", sorting=" + sorting + "]";
	}




}
