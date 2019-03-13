package com.zilker.onlinejobsearch.customException;

public class EmailAlreadyExistsException extends ApplicationException{

	private static final long serialVersionUID = -99813583418632886L;
	
	private String errorCode="EMAIL_ERR_EXISTS";
	private String errorMessage="EMAIL ALREADY REGISTERED";
	
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
