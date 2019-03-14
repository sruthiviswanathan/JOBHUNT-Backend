package com.zilker.onlinejobsearch.customException;

public class CompanyAlreadyExistsException extends ApplicationException {

	private static final long serialVersionUID = -393826782734645832L;
	private String errorCode="COMPANY_ERR_EXISTS";
	private String errorMessage="COMPANY ALREADY REGISTERED";
	
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
