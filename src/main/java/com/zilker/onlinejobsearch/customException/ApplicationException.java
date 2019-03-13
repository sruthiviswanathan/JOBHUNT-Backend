package com.zilker.onlinejobsearch.customException;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = -4187677285654257304L;
	
	private String errorCode;
	private String errorMessage;
	
	public ApplicationException() {
		
	}
	
	public ApplicationException(String ErrorCode, String ErrorMessage) {
		this.errorCode="";
		this.errorMessage="";
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
