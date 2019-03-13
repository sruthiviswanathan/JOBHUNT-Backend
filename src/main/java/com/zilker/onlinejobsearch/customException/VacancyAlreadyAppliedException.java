package com.zilker.onlinejobsearch.customException;

public class VacancyAlreadyAppliedException extends ApplicationException {

	private static final long serialVersionUID = -1883081204249150558L;
	private String errorCode="APPLY_ERR";
	private String errorMessage="ALREADY APPLIED";
	
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
