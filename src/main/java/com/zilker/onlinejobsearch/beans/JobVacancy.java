package com.zilker.onlinejobsearch.beans;

public class JobVacancy {
	
	private int companyId,jobId,vacancyCount,flag;
	private String location,jobDescription,vacancyStatus,companyName,jobRole;
	private float salary;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public int getVacancyCount() {
		return vacancyCount;
	}
	public void setVacancyCount(int vacancyCount) {
		this.vacancyCount = vacancyCount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getVacancyStatus() {
		return vacancyStatus;
	}
	public void setVacancyStatus(String vacancyStatus) {
		this.vacancyStatus = vacancyStatus;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	
	
	

}
