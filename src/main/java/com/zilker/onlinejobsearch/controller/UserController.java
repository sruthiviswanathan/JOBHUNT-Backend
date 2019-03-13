package com.zilker.onlinejobsearch.controller;


import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zilker.onlinejobsearch.beans.ApplyJob;

import com.zilker.onlinejobsearch.beans.JobRequest;
import com.zilker.onlinejobsearch.beans.LoginResponse;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.beans.UserDetails;
import com.zilker.onlinejobsearch.customException.ApplicationException;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.JobDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;
import com.zilker.onlinejobsearch.utils.ResponseGeneratorUtil;

@RestController
public class UserController {

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	CompanyDelegate companyDelegate;

	@Autowired
	JobDelegate jobDelegate;

	@Autowired
	ResponseGeneratorUtil responseUtil;
	/*
	 * controller that does login process and redirects user based on their role
	 */
	@GetMapping(value = "/users")
	public <T> ResponseEntity<?> loginProcess(@RequestBody User user) {
		
		ArrayList<LoginResponse>loginResponse= null;
		try {
			
			user.setUserId(userDelegate.fetchUserId(user.getEmail()));
			loginResponse = userDelegate.login(user);
			return responseUtil.successResponse(loginResponse);
		
		}
		catch(ApplicationException e){	
			return responseUtil.errorResponse(e);
		}
		
	}

	/*
	 * controller that does register process
	 */
	@PostMapping(value = "/users")
	public <T> ResponseEntity<?> registerProcess(@RequestBody User user) {
		ArrayList<LoginResponse> registerResponse = null;
		try {

			user.setUserId(userDelegate.fetchUserId(user.getEmail()));
			registerResponse = userDelegate.register(user);
			return responseUtil.successResponse(registerResponse);
			
		} 
//		catch (SQLIntegrityConstraintViolationException e) {
//			
//			return responseUtil.errorResponse("SQLIntegrityConstraintViolationException","Email Id already Exists");
//		}
		catch (Exception e) {
			return responseUtil.errorResponse("Exception","Oops Exception occured");
		}
		
	}

	/*
	 * controller for admin registration
	 */
	@PostMapping(value = "/users-admin")
	public  <T> ResponseEntity<?> registerAdminProcess(@RequestBody User user) {
		ArrayList<LoginResponse> loginResponse = null;
		try {
			
			loginResponse = userDelegate.registerAsAdmin(user);
			return responseUtil.successResponse(loginResponse);
			
		} 
//		catch (SQLIntegrityConstraintViolationException e) {
//			
//			return responseUtil.errorResponse("SQLIntegrityConstraintViolationException","Email Id already Exists");
//		
//		}
		catch (Exception e) {
			
			return responseUtil.errorResponse("Exception","Oops Exception occured");
		}
		
	}

	/*
	 * controller for displaying applied jobs to user
	 */
	@GetMapping(value = "/users/jobs")
	public <T> ResponseEntity<?> DisplayAppliedJobs(@RequestParam("id") int userId) {
		ArrayList<ApplyJob> appliedJobs = null;
		try {
				appliedJobs = companyDelegate.viewAppliedJobs(userId);
				return responseUtil.successResponse(appliedJobs);
				
		} catch (Exception e) {
			
			return responseUtil.errorResponse("Exception","Oops Exception occured");
		}
		
	}

	/*
	 * controller for fetching user details
	 */
	@GetMapping(value = "/users/{id}")
	public <T> ResponseEntity<?> ViewUsers(@PathVariable("id")int userId) {
		ArrayList<UserDetails> userDetails = null;
		try {
				userDetails = userDelegate.retrieveUserData(userId);
				return responseUtil.successResponse(userDetails);
				
		} catch (Exception e) {
			
			return responseUtil.errorResponse("Exception","Oops Exception occured");
		}		
	}

	/*
	 * controller for updating user profile
	 */
	@PutMapping(value = "/users/{id}")
	public  ResponseEntity<?> UpdateUsers(@PathVariable("id")int userId,@RequestBody User user) {	

		try {
				if (userDelegate.updateUserProfile(user, userId)) {
					return responseUtil.generateMessage("Success");
				}else {
				return responseUtil.generateMessage("Error");
				}
		} catch (Exception e) {

			return responseUtil.errorResponse("Exception","Oops Exception occured");
		}
		
	}

	@PostMapping(value = "/users-request")
	public ResponseEntity<?> RequestVacancy(@RequestParam("id") int userId,@RequestParam("email") String email,@RequestBody JobRequest jobRequest){

		try {
			
				if (userDelegate.requestNewVacancy(email, userId, jobRequest)) {
					return responseUtil.generateMessage("Success");
				} else {
				return responseUtil.generateMessage("Error");
				}
		} catch (Exception e) {
			return responseUtil.errorResponse("Exception","Oops Exception occured");
		}
	}

	@GetMapping(value = "/admin")
	public <T> ResponseEntity<?> AdminDetails(@RequestParam("id") int userId) {
		ArrayList<Integer> admin = new ArrayList<Integer>();
		try {
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				admin.add(companyDelegate.numberOfAppliedUsers(companyId));
				admin.add(companyDelegate.numberOfVacancyPublished(companyId));
				return responseUtil.successResponse(admin);
		} catch (Exception e) {
			return responseUtil.errorResponse("Exception","Oops Exception occured");	
		}
		
	}

}
