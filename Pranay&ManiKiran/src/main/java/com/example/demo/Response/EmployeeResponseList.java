package com.example.demo.Response;

import java.util.List;

import com.example.demo.Model.Employee;

public class EmployeeResponseList {
	
	private long toatalNoOfElements;
	
	private Integer totalPages;
	
	private List<Employee> list;

	public List<Employee> getList() {
		return list;
	}

	public void setList(List<Employee> list) {
		this.list = list;
	}

	public long getToatalNoOfElements() {
		return toatalNoOfElements;
	}

	public void setToatalNoOfElements(long toatalNoOfElements) {
		this.toatalNoOfElements = toatalNoOfElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

}
