package com.zilker.onlinejobsearch.customException;



public class ApplicationException extends Exception {

	private static final long serialVersionUID = -4187677285654257304L;
	
	private String errorCode;
	private String errorMessage;
	private Object errorData;
	public ApplicationException() {
		
	}
	
	public ApplicationException(String ErrorCode, Object ErrorMessage) {
		this.errorCode="";
		this.errorMessage="";
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	

	

	
	

}
