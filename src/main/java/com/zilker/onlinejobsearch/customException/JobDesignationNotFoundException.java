package com.zilker.onlinejobsearch.customException;

public class JobDesignationNotFoundException extends ApplicationException{

	private static final long serialVersionUID = 803533884269287568L;
	private String errorCode="JOB_DESIGNATION_ERR";
	private String errorMessage="JOB DESIGNATION NOT FOUND";
	
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
