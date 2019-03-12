package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
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
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.LoginResponse;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.beans.UserDetails;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.JobDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;

@RestController
public class UserController {

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	CompanyDelegate companyDelegate;

	@Autowired
	JobDelegate jobDelegate;

	/*
	 * controller that does login process and redirects user based on their role
	 */
	@GetMapping(value = "/users")
	public ArrayList<LoginResponse> loginProcess(@RequestBody User user) {
		
		ArrayList<LoginResponse>loginResponse= null;
		try {
			user.setUserId(userDelegate.fetchUserId(user.getEmail()));
			loginResponse = userDelegate.login(user);
		}catch(Exception e){

		}
		return loginResponse;
	}

	/*
	 * controller that does register process
	 */
	@PostMapping(value = "/users")
	public ArrayList<LoginResponse> registerProcess(@RequestBody User user) {
		ArrayList<LoginResponse> registerResponse = null;
		try {

			user.setUserId(userDelegate.fetchUserId(user.getEmail()));
			registerResponse = userDelegate.register(user);
			
		} catch (SQLIntegrityConstraintViolationException e) {
		}
		catch (Exception e) {
			
		}
		return registerResponse;
	}

	/*
	 * controller for admin registration
	 */
	@PostMapping(value = "/users-admin")
	public ArrayList<LoginResponse> registerAdminProcess(@RequestBody User user) {
		ArrayList<LoginResponse> loginResponse = null;
		try {
			loginResponse = userDelegate.registerAsAdmin(user);
			
		} catch (SQLIntegrityConstraintViolationException e) {
			
		} catch (Exception e) {
			
		}
		return loginResponse;
	}

	/*
	 * controller for displaying applied jobs to user
	 */
	@GetMapping(value = "/users/jobs")
	public ArrayList<ApplyJob> DisplayAppliedJobs(@RequestParam("id") int userId) {
		ArrayList<ApplyJob> appliedJobs = null;
		try {
				appliedJobs = companyDelegate.viewAppliedJobs(userId);
		
		} catch (Exception e) {
			
		}
		return appliedJobs;
	}

	/*
	 * logout controller
	 */
//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public ModelAndView Logout(HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView model = new ModelAndView("home");
//		try {
//
//			HttpSession session = request.getSession();
//			if (session != null) {
//
//				response.setHeader("Cache-Control", "no-cache");
//				response.setHeader("Pragma", "no-cache");
//				response.setDateHeader("max-age", 0);
//				response.setDateHeader("Expires", 0);
//				session.invalidate();
//			}
//
//		} catch (Exception e) {
//			model = new ModelAndView("error");
//		}
//		return model;
//	}

	/*
	 * controller for fetching user details and displaying user profile
	 */
	@GetMapping(value = "/users/{id}")
	public ArrayList<UserDetails> ViewUsers(@PathVariable("id")int userId) {
		ArrayList<UserDetails> userDetails = null;
		try {
				userDetails = userDelegate.retrieveUserData(userId);
		} catch (Exception e) {
			
		}
		return userDetails;
	}

	/*
	 * controller for updating user profile
	 */
	@PutMapping(value = "/users/{id}")
	public void UpdateUsers(@PathVariable("id")int userId,@RequestBody User user,HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		try {
				if (userDelegate.updateUserProfile(user, userId)) {
					out.print("success");
					out.flush();
				}
			
		} catch (Exception e) {

			out.print("error");
			out.flush();
		}
	}

	/*
	 * controller for fetching job designation and displaying request vacancy page
	 */
	@RequestMapping(value = "/users/request", method = RequestMethod.GET)
	public ModelAndView ViewRequestVacancy(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelAndView model = new ModelAndView("requestvacancy");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				model.addObject("jobs", job);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	@RequestMapping(value = "/users/request", method = RequestMethod.POST)
	public void RequestVacancy(@RequestParam("job") String jobDesignation, @RequestParam("location") String location,
			@RequestParam("salary") String salary, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		try {
			if (session.getAttribute("email") == null) {
				response.sendRedirect("home.jsp");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				String email = (String) session.getAttribute("email");
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				request.setAttribute("jobs", job);
				if (userDelegate.requestNewVacancy(email, userId, jobDesignation, location, salary)) {
					out.print("success");
					out.flush();
				} else {
					out.print("error");
					out.flush();
				}
			}
		} catch (Exception e) {
			out.print("error");
			out.flush();
		}
	}

	@RequestMapping(value = "/users/admin", method = RequestMethod.GET)
	public ModelAndView showAdminPage(HttpSession session) {
		ModelAndView model = new ModelAndView("admin");
		try {
			if (session.getAttribute("email") == null) {
				new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				model.addObject("appliedUsers", companyDelegate.numberOfAppliedUsers(companyId));
				model.addObject("postedJobs", companyDelegate.numberOfVacancyPublished(companyId));
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

}
