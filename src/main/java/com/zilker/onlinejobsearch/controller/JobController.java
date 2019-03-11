package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.zilker.onlinejobsearch.beans.ApplyJob;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobVacancy;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.JobDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;

@RestController
public class JobController {

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	JobDelegate jobDelegate;

	@Autowired
	CompanyDelegate companyDelegate;

	@GetMapping("/jobdesignation/{job}/{id}")
	public ArrayList<JobVacancy> findJobs(@PathVariable("job") String jobDesignation,@PathVariable("id") int userId) throws IOException {
		ArrayList<JobVacancy> vacancyDetails = null;
		try {
				int jobId = jobDelegate.fetchJobId(jobDesignation);
				if (jobId == 0) {
					
				} else {
					vacancyDetails = jobDelegate.retrieveVacancyByJob1(jobId, userId);					
				}

		} catch (SQLException e) {
	
		}
		return vacancyDetails;
	}

	
	@PostMapping(value = "/company/jobs/apply")
	public void ApplyJobs(@RequestParam("id") int userId,@RequestParam("email") String email,@RequestBody ApplyJob applyJobs,HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/html;charset=UTF-8");
			int companyId = companyDelegate.fetchCompanyId(applyJobs.getCompanyName());
			int jobId = jobDelegate.fetchJobId(applyJobs.getJobRole());
			if (userDelegate.applyForJob(companyId,jobId,applyJobs.getLocation(),userId,email)) {
				out.print("success");
				out.flush();
			} 
		}
		catch (SQLIntegrityConstraintViolationException e) {
			out.print("error");
			out.flush();
		}
		catch (Exception e) {
			out.print("error");
			out.flush();
		}
	}

	
	@GetMapping(value = "/jobs")
	public ArrayList<JobMapping> GetAllJobDesignations() {
		ArrayList<JobMapping> job = null;
		try {
			job = jobDelegate.displayJobs();
		} catch (Exception e) {
			
		}
		return job;
	}

	@PostMapping(value = "/company/vacancy")
	public void PublishNewVacancy(@RequestParam("id") int userId,@RequestBody JobVacancy jobVacancy,HttpServletResponse response)
			throws IOException {

		PrintWriter out = response.getWriter();
		try {
			
			int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
			if (companyDelegate.publishVacancy(userId,companyId,jobVacancy)) {
				companyDelegate.compareVacancyWithRequest(jobVacancy.getJobId(),jobVacancy.getLocation());
				out.print("success");
				out.flush();
			} 
			
		} catch (SQLIntegrityConstraintViolationException e) {
			out.print("error");
			out.flush();
		}
		catch (Exception e) {
			out.print("error");
			out.flush();
		}
	}


	@PostMapping(value = "/jobs")
	public ArrayList<JobMapping> AddNewJobDesignation(@RequestParam("id") int userId,@RequestBody JobMapping jobMapping)
			throws ServletException, IOException {
		ArrayList<JobMapping> job = null;
		try {
		
				if (jobDelegate.addNewJob(jobMapping, userId)) {
					job = jobDelegate.displayJobs();
				} 
			
		} catch (SQLException e) {
			
		}
		return job;
	}
}
