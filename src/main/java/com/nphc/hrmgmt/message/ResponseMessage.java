package com.nphc.hrmgmt.message;

public class ResponseMessage {

	private String message;
	private int statusCode;

	public ResponseMessage(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}

	public ResponseMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
