package com.example.demo.Response;

import org.springframework.stereotype.Component;

@Component
public class EmployeeResponse {
	
	
	private String message;
	
	private String statusCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	

}
