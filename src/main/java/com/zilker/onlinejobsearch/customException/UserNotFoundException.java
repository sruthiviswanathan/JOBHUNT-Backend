package com.zilker.onlinejobsearch.customException;



public class UserNotFoundException extends ApplicationException  {


	private static final long serialVersionUID = 1966448304997032702L;
	
	private String errorCode="USER_ERR";
	private String errorMessage="USER NOT FOUND";
	
	
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
