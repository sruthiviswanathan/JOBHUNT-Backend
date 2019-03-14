package com.zilker.onlinejobsearch.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.zilker.onlinejobsearch.beans.ApplyJob;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobVacancy;
import com.zilker.onlinejobsearch.customException.ApplicationException;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.JobDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;
import com.zilker.onlinejobsearch.utils.ResponseGeneratorUtil;

@RestController
public class JobController {

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	JobDelegate jobDelegate;

	@Autowired
	CompanyDelegate companyDelegate;

	@Autowired
	ResponseGeneratorUtil responseUtil;
	
	@GetMapping("/jobdesignation/{job}/{id}")
	public <T> ResponseEntity<?> findJobs(@PathVariable("job") String jobDesignation,@PathVariable("id") int userId) {
		ArrayList<JobVacancy> vacancyDetails = null;
		try {
				int jobId = jobDelegate.fetchJobId(jobDesignation);
				if (jobId == 0) {
					return responseUtil.errorResponse("noJobDesignation","invalid job Designation");
				} else {
					vacancyDetails = jobDelegate.retrieveVacancyByJob1(jobId, userId);	
					return responseUtil.successResponse(vacancyDetails);
				}

		} catch (ApplicationException e) {
			return responseUtil.errorResponse(e);
		}
		
	}

	@PostMapping(value = "/company/jobs/apply")
	public ResponseEntity<?> ApplyJobs(@RequestParam("id") int userId,@RequestParam("email") String email,@RequestBody ApplyJob applyJobs){
		try {
			
			int companyId = companyDelegate.fetchCompanyId(applyJobs.getCompanyName());
			int jobId = jobDelegate.fetchJobId(applyJobs.getJobRole());
			if (userDelegate.applyForJob(companyId,jobId,applyJobs.getLocation(),userId,email)) {
				return responseUtil.generateMessage("Success");
			}else { 
			return responseUtil.generateMessage("Error");
			}
		}
	
		catch (ApplicationException e) {
			return responseUtil.errorResponse(e);
		}
	}

	
	@GetMapping(value = "/jobs")
	public <T> ResponseEntity<?> GetAllJobDesignations() {
		ArrayList<JobMapping> job = null;
		try {
			job = jobDelegate.displayJobs();
			return responseUtil.successResponse(job);
		} catch (ApplicationException e) {
			return responseUtil.errorResponse(e);
		}
	}

	@PostMapping(value = "/company/vacancy")
	public ResponseEntity<?> PublishNewVacancy(@RequestParam("id") int userId,@RequestBody JobVacancy jobVacancy){
		try {
			
			int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
			if (companyDelegate.publishVacancy(userId,companyId,jobVacancy)) {
				companyDelegate.compareVacancyWithRequest(jobVacancy.getJobId(),jobVacancy.getLocation());
				return responseUtil.generateMessage("Success");
			} else {
			
				return responseUtil.generateMessage("Error");
			}
		} 
		catch (ApplicationException e) {
			return responseUtil.errorResponse(e);
		}
	}


	@PostMapping(value = "/jobs")
	public <T> ResponseEntity<?> AddNewJobDesignation(@RequestParam("id") int userId,@RequestBody JobMapping jobMapping){
		ArrayList<JobMapping> job = null;
		try {
		
				if (jobDelegate.addNewJob(jobMapping, userId)) {
					job = jobDelegate.displayJobs();
					return responseUtil.successResponse(job);
				} else {
				
					return responseUtil.errorResponse("Error","Error Adding jobDesignation");
				}
		} catch (ApplicationException e) {
			return responseUtil.errorResponse(e);
		}
		
	}
}
