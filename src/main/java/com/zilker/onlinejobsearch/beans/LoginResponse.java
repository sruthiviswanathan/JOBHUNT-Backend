package com.zilker.onlinejobsearch.beans;

import java.util.ArrayList;

public class LoginResponse {

	private int role,userId;
	private String userName;
	private ArrayList<CompanyDetails> companyDetails;
	private ArrayList<Integer> adminDetails;
	

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public ArrayList<CompanyDetails> getCompanyDetails() {
		return companyDetails;
	}
	public void setCompanyDetails(ArrayList<CompanyDetails> companyDetails) {
		this.companyDetails = companyDetails;
	}
	public ArrayList<Integer> getAdminDetails() {
		return adminDetails;
	}
	public void setAdminDetails(ArrayList<Integer> adminDetails) {
		this.adminDetails = adminDetails;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
