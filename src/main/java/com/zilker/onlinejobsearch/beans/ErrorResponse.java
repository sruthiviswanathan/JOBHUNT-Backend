package com.zilker.onlinejobsearch.beans;


public class ErrorResponse  implements java.io.Serializable {


	private static final long serialVersionUID = -6348987034948364168L;

	private String errorCode;
	private String errorMessage;
	private Object errorData;
	
	
	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public ErrorResponse(String errorCode, String errorMessage,Object errorData) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorData = errorData;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getErrorData() {
		return errorData;
	}

	public void setErrorData(Object errorData) {
		this.errorData = errorData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	
	
}
