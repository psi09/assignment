package com.sony.model;

public class APIStatus {

	private int statusCode;
	private String status;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "APIStatus [statusCode=" + statusCode + ", status=" + status + "]";
	}
	
}
