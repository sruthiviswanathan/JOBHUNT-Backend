package com.zilker.onlinejobsearch.customException;

public class VacancyAlreadyPublishedException extends ApplicationException{

	private static final long serialVersionUID = -5238980638695107041L;
	private String errorCode="PUBLISH_ERR";
	private String errorMessage="VACANCY ALREADY PUBLISHED";
	
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
