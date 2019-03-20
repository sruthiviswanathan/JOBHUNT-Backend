package com.zilker.onlinejobsearch.delegate;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.ApplyJob;
import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.CompanyDetails;
import com.zilker.onlinejobsearch.beans.CompanyReviews;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobRequest;
import com.zilker.onlinejobsearch.beans.JobReviews;
import com.zilker.onlinejobsearch.beans.JobVacancy;
import com.zilker.onlinejobsearch.beans.LoginResponse;
import com.zilker.onlinejobsearch.beans.Technology;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.beans.UserDetails;
import com.zilker.onlinejobsearch.beans.UserTechnologyMapping;
import com.zilker.onlinejobsearch.constants.ErrorCodes;
import com.zilker.onlinejobsearch.customException.ApplicationException;
import com.zilker.onlinejobsearch.customException.EmailAlreadyExistsException;
import com.zilker.onlinejobsearch.customException.UserNotFoundException;
import com.zilker.onlinejobsearch.customException.VacancyAlreadyAppliedException;
import com.zilker.onlinejobsearch.dao.UserDAO;

@Service
public class UserDelegate {

	public LoginResponse register(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		LoginResponse login = new LoginResponse();
		ArrayList<CompanyDetails> displayCompanies = null;
		try {
			CompanyDelegate companyDelegate = new CompanyDelegate();

			UserDAO userdao = new UserDAO();
			displayCompanies = companyDelegate.displayCompanies();
			login.setCompanyDetails(displayCompanies);
			if (ifEmailAlreadyExists(user.getEmail())) {
				throw new EmailAlreadyExistsException();
			} else {
				if (userdao.register(user)) {
					int userId = fetchUserId(user.getEmail());
					insertIntoUser(userId, user.getEmail());
					addSkillsToProfile(user.getSkills(), userId);
					login.setUserId(userId);
				}

			}
		} catch (EmailAlreadyExistsException e) {
			e.setErrorData(login);
			throw e;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE, ErrorCodes.SQLERRORMESSAGE);
		} catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE, ErrorCodes.GERNERICERRORMESSAGE);
		}
		return login;
	}

	public void addTechnologyDetails(UserTechnologyMapping usertechnology) throws ApplicationException {
		// TODO Auto-generated method stub

		try {
			UserDAO userdao = new UserDAO();
			userdao.addTechnologyDetails(usertechnology);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}

	}

	public String fetchUserNameById(int userId) throws ApplicationException {
		String userName = "";
		try {

			UserDAO userDao = new UserDAO();
			userName = userDao.fetchUserNameById(userId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return userName;
	}

	public int fetchTechnologyId(Technology technology) throws ApplicationException {
		// TODO Auto-generated method stub
		int technologyId = 0;
		// TODO Auto-generated method stub
		try {
			UserDAO userDao = new UserDAO();
			technologyId = userDao.fetchTechnologyId(technology);
			
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return technologyId;
	}

	public int fetchUserId(String email) throws ApplicationException {
		int userId = 0;
		// TODO Auto-generated method stub
		try {
			UserDAO userDao = new UserDAO();
			userId = userDao.fetchUserId(email);		
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return userId;
	}

	public LoginResponse login(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		int role = 0;
		LoginResponse login = new LoginResponse();
		ArrayList<CompanyDetails> displayCompanies = null;
		ArrayList<Integer> admin = new ArrayList<Integer>();
		try {

			UserDAO userdao = new UserDAO();
			CompanyDelegate companyDelegate = new CompanyDelegate();
			role = userdao.login(user);
			login.setRole(role);
			login.setUserId(user.getUserId());
			String userName = fetchUserNameById(user.getUserId());
			login.setUserName(userName);
			if (role == 0) {
				displayCompanies = companyDelegate.displayCompanies();
				login.setCompanyDetails(displayCompanies);
				throw new UserNotFoundException();
			} else if (role == 1) {
				displayCompanies = companyDelegate.displayCompanies();
				login.setCompanyDetails(displayCompanies);
			} else if (role == 2) {

				int companyId = fetchCompanyIdByAdmin(user.getUserId());
				admin.add(companyId);
				admin.add(companyDelegate.numberOfAppliedUsers(companyId));
				admin.add(companyDelegate.numberOfVacancyPublished(companyId));
				login.setAdminDetails(admin);
			}

		} catch (UserNotFoundException e) {
			e.setErrorData(login);
			throw e;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return login;
	}

	public boolean requestNewVacancy(String email, int userId, JobRequest jobRequest) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			User user = new User();
			user.setUserId(userId);
			jobRequest.setEmail(email);
			flag = userDao.requestNewVacancy(jobRequest, user);
		}catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean reviewAndRateCompany(int userId, int companyId, CompanyReviews reviewsRating)
			throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			User user = new User();
			user.setUserId(userId);
			reviewsRating.setCompanyId(companyId);
			flag = userDao.reviewAndRateCompany(user, reviewsRating);
			if (reviewsRating.getJobReviews() != null) {
				if (interviewProcess(userId, companyId, reviewsRating)) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean interviewProcess(int userId, int companyId, CompanyReviews reviews) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			User user = new User();
			JobReviews jobReviews = new JobReviews();
			JobMapping jobmapping = new JobMapping();
			JobDelegate jobDelegate = new JobDelegate();
			int jobId = jobDelegate.fetchJobId(reviews.getJobReviews().getJobRole());
			jobmapping.setJobId(jobId);
			user.setUserId(userId);
			jobReviews.setCompanyId(companyId);
			jobReviews.setInterviewProcess(reviews.getJobReviews().getInterviewProcess());
			flag = userDao.interviewProcess(user, jobReviews, jobmapping);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public int checkPasswordBeforeDelete(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.checkPasswordBeforeDelete(user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public ArrayList<Technology> displayTechnologies(Technology technology) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<Technology> tech = new ArrayList<Technology>();
		try {
			UserDAO userDao = new UserDAO();
			tech = userDao.displayTechnologies(technology);

		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return tech;

	}

	public Company retrieveReview(int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		Company company = new Company();
		ArrayList<CompanyReviews> companyReviews = null;
		ArrayList<CompanyDetails> companyDetails = null;
		try {
			CompanyDelegate companyDelegate = new CompanyDelegate();
			UserDAO userDao = new UserDAO();
			companyReviews = userDao.retrieveReview(companyId);
			companyDetails = companyDelegate.retrieveCompanyDetails(companyId);	
			company.setCompanyDetails(companyDetails);
			company.setCompanyReviews(companyReviews);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return company;
	}

	public Company retrieveInterviewProcess(int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		//ArrayList<Company> companies = new ArrayList<Company>();
		Company company = new Company();
		ArrayList<CompanyDetails> companyDetails = new ArrayList<CompanyDetails>();
		ArrayList<JobReviews> companyInterviews = new ArrayList<JobReviews>();
		try {
			UserDAO userDao = new UserDAO();
			CompanyDelegate companyDelegate = new CompanyDelegate();
			companyInterviews = userDao.retrieveInterviewProcess(companyId);
			companyDetails = companyDelegate.retrieveCompanyDetails(companyId);
			company.setCompanyDetails(companyDetails);
			company.setCompanyInterviews(companyInterviews);
			//companies.add(company);
		}catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}

		return company;
	}

	public UserDetails retrieveUserData(int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		UserDetails user = new UserDetails();
		ArrayList<User> userData = null;
		try {
			
			UserDAO userDao = new UserDAO();
			UserTechnologyMapping userTechnologyMapping = new UserTechnologyMapping();
			userData = userDao.retrieveUserData(userId);
			ArrayList<UserTechnologyMapping> userTechnology = displayUserTechnologies(userTechnologyMapping, userId);
			user.setUserTechnology(userTechnology);
			user.setUser(userData);
			
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return user;
	}

	public boolean ifEmailAlreadyExists(String email) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.ifEmailAlreadyExists(email);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean ifTechnologyIdExists(Technology technology) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.ifTechnologyIdExists(technology);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public LoginResponse registerAsAdmin(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		int flag1 = 0;
		// ArrayList<LoginResponse> loginResponse = new ArrayList<LoginResponse>();
		LoginResponse login = new LoginResponse();
		ArrayList<Integer> admin = new ArrayList<Integer>();
		try {
			CompanyDelegate companyDelegate = new CompanyDelegate();
			UserDAO userDao = new UserDAO();
			ArrayList<CompanyDetails> displayCompanies = null;
			String companyname = companyDelegate.fetchCompanyName(Integer.parseInt(user.getCompany()));
			int companyId = Integer.parseInt(user.getCompany());
			user.setCompany(companyname);
			user.setDesignation("admin");
			user.setRoleId(2);

			if (ifEmailAlreadyExists(user.getEmail())) {
				displayCompanies = companyDelegate.displayCompanies();
				login.setCompanyDetails(displayCompanies);
				throw new EmailAlreadyExistsException();

			} else {
				if (userDao.registerAsAdmin(user)) {
					int userId = fetchUserId(user.getEmail());
					insertIntoUser(userId, user.getEmail());
					if (userId != 0) {
						flag1 = insertIntoAdmin(userId, companyId);
						if (flag1 == 1) {
							companyDelegate.insertIntoCompanyDetails(userId, companyId);
						}
					}
					admin.add(companyId);
					admin.add(companyDelegate.numberOfAppliedUsers(companyId));
					admin.add(companyDelegate.numberOfVacancyPublished(companyId));
					login.setAdminDetails(admin);
					login.setUserId(userId);
				}
			}
		} catch (EmailAlreadyExistsException e) {
			e.setErrorData(login);
			throw e;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return login;
	}

	public int insertIntoAdmin(int userId, int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.insertIntoAdmin(userId, companyId);
		}catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public void insertIntoUser(int userId, String email) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			User user = new User();
			UserDAO userDao = new UserDAO();
			user.setUserId(userId);
			user.setEmail(email);
			userDao.insertIntoUser(user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public int fetchCompanyIdByAdmin(int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		int companyId = 0;
		try {
			UserDAO userDao = new UserDAO();
			companyId = userDao.fetchCompanyIdByAdmin(userId);
			return companyId;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public boolean updateUserName(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.updateUserName(user);
			return flag;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public boolean updateCompanyName(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.updateCompanyName(user);
			return flag;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public boolean updateUserDesignation(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.updateUserDesignation(user);
			return flag;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public ArrayList<UserTechnologyMapping> displayUserTechnologies(UserTechnologyMapping userTechnologyMapping,
			int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<UserTechnologyMapping> usertechnology = new ArrayList<UserTechnologyMapping>();
		try {
			UserDAO userDao = new UserDAO();
			usertechnology = userDao.displayUserTechnologies(userTechnologyMapping, userId);

		}catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return usertechnology;
	}

	public boolean updateUserTechnology(UserTechnologyMapping userTechnologyMapping, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.updateUserTechnology(userTechnologyMapping, user);
			return flag;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public int addNewTechnology(Technology technology, int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		int technologyId = 0;
		try {
			UserDAO userDao = new UserDAO();
			technologyId = userDao.addNewTechnology(technology, userId);
			return technologyId;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public int deleteUserAccount(User user) throws ApplicationException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.deleteUserAccount(user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean markContacted(int userId, int companyId, int jobId, ApplyJob applyJobs) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {

			UserDAO userDao = new UserDAO();
			User user = new User();
			user.setUserId(userId);
			user.setEmail(applyJobs.getEmail());
			flag = userDao.markContacted(companyId, jobId, applyJobs, user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean applyForJob(int companyId, int jobId, String location, int userId, String email)
			throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			ApplyJob company = new ApplyJob();
			User user = new User();
			user.setEmail(email);
			user.setUserId(userId);
			company.setCompanyId(companyId);
			company.setJobId(jobId);
			company.setLocation(location);
			if (ifAlreadyAppliedJob(company, user)) {
				throw new VacancyAlreadyAppliedException();
			} else {
				flag = userDao.applyForJob(company, user);
			}
		} catch (VacancyAlreadyAppliedException e) {
			throw e;
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	private boolean ifAlreadyAppliedJob(ApplyJob company, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.ifAlreadyAppliedJob(company, user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean deleteTechnologyDetails(UserTechnologyMapping userTechnology, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.deleteTechnologyDetails(userTechnology, user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public void addSkillsToProfile(String skills, int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			int technologyId = 0;
			String[] technology;
			Technology techh = new Technology();
			UserTechnologyMapping usertechnology = new UserTechnologyMapping();
			if (skills != "") {
				technology = skills.split("@");
				if (technology != null) {

					for (int i = 0; i < technology.length; i++) {

						usertechnology.setUserId(userId);
						techh.setTechnology(technology[i]);
						technologyId = fetchTechnologyId(techh);
						if (technologyId == 0) {
							techh.setTechnology(technology[i]);
							technologyId = addNewTechnology(techh, userId);
							usertechnology.setTechnologyId(technologyId);
							addTechnologyDetails(usertechnology);
						} else {
							usertechnology.setTechnologyId(technologyId);
							addTechnologyDetails(usertechnology);
						}
					}

				}
			}
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public boolean updateUserProfile(User user, int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			int technologyId = 0;
			String[] technology;
			UserTechnologyMapping usertechnology = new UserTechnologyMapping();
			UserTechnologyMapping userTechnologyMapping = new UserTechnologyMapping();
			ArrayList<UserTechnologyMapping> userTechnology = null;

			Technology techh = new Technology();
			user.setUserId(userId);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			user.setCurrentTime(dtf.format(now));
			if (updateUserName(user)) {

			}
			if (updateCompanyName(user)) {

			}
			if (updateUserDesignation(user)) {

			}

			if (user.getSkills() != "") {
				technology = user.getSkills().split("@");
				if (technology != null) {

					userTechnology = displayUserTechnologies(userTechnologyMapping, userId);
					if (userTechnology.isEmpty()) {
					} else {
						deleteTechnologyDetails(userTechnologyMapping, user);
					}
					for (int i = 0; i < technology.length; i++) {

						usertechnology.setUserId(user.getUserId());
						techh.setTechnology(technology[i]);
						technologyId = fetchTechnologyId(techh);
						if (technologyId == 0) {
							techh.setTechnology(technology[i]);
							technologyId = addNewTechnology(techh, userId);
							usertechnology.setTechnologyId(technologyId);
							addTechnologyDetails(usertechnology);
						} else {
							usertechnology.setTechnologyId(technologyId);
							addTechnologyDetails(usertechnology);
						}
					}

				}
			}
			flag = true;
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean UpdateVacancy(int oldJobId,String location,int companyId, int userId, JobVacancy jobVacancy)
			throws ApplicationException {
		// TODO Auto-generated method stub
		boolean status = false;
		try {

			User user = new User();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			user.setCurrentTime(dtf.format(now));
			CompanyDelegate companyDelegate = new CompanyDelegate();
			jobVacancy.setOldJobId(oldJobId);
			jobVacancy.setOldLocation(location);
			user.setUserId(userId);
			jobVacancy.setCompanyId(companyId);
			if (companyDelegate.updateVacancyJobId(jobVacancy, user)) {

				status = true;
			}

			if (companyDelegate.updateVacancyLocation(jobVacancy, user)) {

				status = true;
			}

			if (companyDelegate.updateVacancyDescription(jobVacancy, user)) {

				status = true;
			}

			if (companyDelegate.updateVacancySalary(jobVacancy, user)) {
				status = true;
			}
			if (jobVacancy.getVacancyCount() == 0) {
				jobVacancy.setVacancyStatus("expired");
			} else {
				jobVacancy.setVacancyStatus("available");

			}
			if (companyDelegate.updateVacancyCount(jobVacancy, user)) {

				status = true;
			}

		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return status;
	}
}
