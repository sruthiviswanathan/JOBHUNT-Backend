package com.zilker.onlinejobsearch.customException;

public class JobDesignationAlreadyExistsException extends ApplicationException {

	private static final long serialVersionUID = -3407800938431522454L;
	private String errorCode="JOB_ERR_EXISTS";
	private String errorMessage="JOB DESIGNATION ALREADY EXISTS";
	
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
