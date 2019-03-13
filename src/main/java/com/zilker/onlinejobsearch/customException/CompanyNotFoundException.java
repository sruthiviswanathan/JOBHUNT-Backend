package com.zilker.onlinejobsearch.customException;

public class CompanyNotFoundException extends ApplicationException{

	private static final long serialVersionUID = 4673382393541402083L;
	private String errorCode="COMPANY_ERR";
	private String errorMessage="COMPANY NOT FOUND";
	
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
