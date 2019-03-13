package com.zilker.onlinejobsearch.beans;

import java.util.List;

public class ResponseAsArray implements java.io.Serializable  {

	private static final long serialVersionUID = 201884431121529859L;
	private boolean isSuccess;
	private List<?> responseBody;

	public ResponseAsArray() {
		super();
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<?> getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(List<?> responseBody) {
		this.responseBody = responseBody;
	}
}
