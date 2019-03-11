package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zilker.onlinejobsearch.beans.ApplyJob;
import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.CompanyDetails;
import com.zilker.onlinejobsearch.beans.CompanyReviews;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobVacancy;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.JobDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;

@RestController
public class CompanyController {

	@Autowired
	CompanyDelegate companyDelegate;

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	JobDelegate jobDelegate;

	/*
	 * fetch all company details and display findByJobs page
	 */
	@GetMapping("/company")
	public ArrayList<CompanyDetails> DisplayAllCompanies(HttpSession session) {
		ArrayList<CompanyDetails> companyDetails=null;
		try {
				companyDetails = companyDelegate.displayCompanies();
			
		} catch (Exception e) {
			
		}
		return companyDetails;
	}

	/*
	 * controller to add a new company
	 */
	@PostMapping("/companies")
	public boolean AddNewCompany(@RequestBody CompanyDetails company) {
		boolean flag= false;
		try {
				flag = companyDelegate.addNewCompany(company);	
		} catch (Exception e) {
			
		}
		return flag;
	}

	/*
	 * controller to retrieve all details and vacancy in company
	 */
	@GetMapping("/company/{companyName}/{id}")
	public  ArrayList<Company> findCompany(@PathVariable("companyName") String companyName,@PathVariable("id") int userId) {
		 ArrayList<Company> companyDetails = null;
		try {			
				int companyId = companyDelegate.fetchCompanyId(companyName);
				if (companyId == 0) {
					
				} else {
					companyDetails = companyDelegate.retrieveVacancyByCompany(companyId,userId);				
				}
			
		} catch (SQLException e) {
			
		}
		return companyDetails;
	}

	/*
	 * controller to find vacancies by location
	 */
	@GetMapping("/location/{location}/{id}")
	public ArrayList<JobVacancy> findByLocation(@PathVariable("location") String location,@PathVariable("id") int userId) {
		ArrayList<JobVacancy> retrieveByLocation= null;
		try {
				retrieveByLocation = companyDelegate.retrieveVacancyByLocation(location, userId);
				
		} catch (SQLException e) {
	
		}
		return retrieveByLocation;
	}

	/*
	 * controller to view all reviews of a company
	 */
	@GetMapping("/company/reviews/{companyName}")
	public ArrayList<Company> viewCompanyReviews(@PathVariable("companyName") String companyName) {
		ArrayList<Company> reviews = null;
		try {			
				int companyId = companyDelegate.fetchCompanyId(companyName);
				reviews = userDelegate.retrieveReview(companyId);
						
		} catch (Exception e) {
			
		}
		return reviews;
	}

	/*
	 * controller to view all reviews of all interview process of a company
	 */
	@GetMapping(value = "/company/interviews/{companyName}")
	public ArrayList<Company> viewReviewsOnInterviewProcess(@PathVariable("companyName") String companyName) {
		ArrayList<Company> interviewProcess = null;
		try {
				int companyId = companyDelegate.fetchCompanyId(companyName);
				interviewProcess = userDelegate.retrieveInterviewProcess(companyId);
			
		} catch (Exception e) {
			
		}
		return interviewProcess;
	}

	/*
	 * controller to rate a company
	 */
	@PostMapping(value = "/company/rate")
	public ArrayList<Company> RateACompany(@RequestParam("id") int userId,@RequestParam("company") String companyName,@RequestBody CompanyReviews reviewsRating) {
			ArrayList<Company> reviews = null;
		try {
			
				int companyId = companyDelegate.fetchCompanyId(companyName);
				//System.out.println(reviewsRating.getJobReviews());
				if (userDelegate.reviewAndRateCompany(userId, companyId,reviewsRating)) {
					reviews = userDelegate.retrieveReview(companyId);
				}
				
		} catch (Exception e) {
		
		}
		return reviews;
	}

	/*
	 * controller to fetch applied users
	 */
	@GetMapping("/applied-users/{id}")
	public ArrayList<ApplyJob> AppliedUsers(@PathVariable("id") int userId) {
		ArrayList<ApplyJob> appliedUsers = null;
		try {
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				 appliedUsers = companyDelegate.viewAppliedUsers(companyId);
				
		} catch (Exception e) {
		
		}
		return appliedUsers;
	}

	
	/*
	 * controller to mark users as contacted
	 */
	@PutMapping(value = "/contacted-users")
	public void UpdateContactedUsers(@RequestParam("id") int userId,@RequestBody ApplyJob applyJobs,HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		try {
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				int jobId = jobDelegate.fetchJobId(applyJobs.getJobRole());
				if (userDelegate.markContacted(userId, companyId,jobId,applyJobs)) {
					System.out.println("yess");
					out.print("success");
					out.flush();
				}
			
		} catch (Exception e) {

			out.print("error");
			out.flush();
		}
	}

	/*
	 * controller to fetch all published jobs by admin
	 */
	@GetMapping(value = "company/jobspublished/{id}")
	public ArrayList<Company> ViewPublishedJobs(@PathVariable("id") int userId) {
		ArrayList<Company> vacancyDetails = null;
		try {

				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				vacancyDetails = companyDelegate.retrieveVacancyByCompanyAdmin(companyId);		
			
		} catch (Exception e) {
			
		}
		return vacancyDetails;
	}

	
////
	/*
	 * controller to update published jobs
	 */
	@RequestMapping(value = "company/jobspublished", method = RequestMethod.POST)
	public ModelAndView UpdatePublishedJobs(@RequestParam("action") String action,@RequestParam("jobdesignation") String jobDesignation,
			@RequestParam("job") String newJobDesignation,@RequestParam("location") String location,
			@RequestParam("description") String jobDescription,@RequestParam("salary") String salary,
			@RequestParam("count") String count,HttpSession session) {
		ModelAndView model = new ModelAndView("viewpublishedjobs");
		try {

			
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				
				int userId = (Integer) session.getAttribute("userId");
				int oldJobId = jobDelegate.fetchJobId(jobDesignation);
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				if ("UPDATE".equals(action)) {
					if(userDelegate.UpdateVacancy(oldJobId,companyId,userId,newJobDesignation,location,jobDescription,salary,count)) {
					model.addObject("status", "updated");
					}
				}else if ("DELETE".equals(action)) {
					if (companyDelegate.removeVacancy(companyId, userId,oldJobId)) {
						model.addObject("status", "deleted");
					}
				}
				ArrayList<Company> vacancyDetails  = companyDelegate.retrieveVacancyByCompanyAdmin(companyId);
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				model.addObject("jobs", job);
				model.addObject("vacancyDetails", vacancyDetails);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}
}
