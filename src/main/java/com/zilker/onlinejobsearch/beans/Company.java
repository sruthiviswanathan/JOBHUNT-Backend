package com.zilker.onlinejobsearch.beans;

import java.util.ArrayList;

/*
 * bean class for company details.
 */
public class Company {

	
	private ArrayList<CompanyDetails> companyDetails;
	private ArrayList<JobVacancy> jobVacancy;
	private ArrayList<CompanyReviews> companyReviews;
	private ArrayList<JobReviews> companyInterviews;
	private ArrayList<JobMapping> jobs;
	
/*
 * Getters and setters for all arrayLists
 */
	

	public ArrayList<JobMapping> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<JobMapping> jobs) {
		this.jobs = jobs;
	}

	public ArrayList<JobReviews> getCompanyInterviews() {
		return companyInterviews;
	}

	public void setCompanyInterviews(ArrayList<JobReviews> companyInterviews) {
		this.companyInterviews = companyInterviews;
	}

	public ArrayList<CompanyReviews> getCompanyReviews() {
		return companyReviews;
	}

	public void setCompanyReviews(ArrayList<CompanyReviews> companyReviews) {
		this.companyReviews = companyReviews;
	}

	public ArrayList<CompanyDetails> getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(ArrayList<CompanyDetails> companyDetails) {
		this.companyDetails = companyDetails;
	}

	public ArrayList<JobVacancy> getJobVacancy() {
		return jobVacancy;
	}

	public void setJobVacancy(ArrayList<JobVacancy> jobVacancy) {
		this.jobVacancy = jobVacancy;
	}



}

