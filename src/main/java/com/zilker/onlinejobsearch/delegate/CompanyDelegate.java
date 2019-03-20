package com.zilker.onlinejobsearch.delegate;


import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.ApplyJob;
import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.CompanyDetails;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobVacancy;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.constants.ErrorCodes;
import com.zilker.onlinejobsearch.customException.ApplicationException;
import com.zilker.onlinejobsearch.customException.CompanyAlreadyExistsException;
import com.zilker.onlinejobsearch.customException.CompanyNotFoundException;
import com.zilker.onlinejobsearch.customException.VacancyAlreadyPublishedException;
import com.zilker.onlinejobsearch.dao.CompanyDAO;

@Service
public class CompanyDelegate {

	public ArrayList<CompanyDetails> displayCompanies() throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<CompanyDetails> comp = null;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.displayCompanies();
		}
		catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return comp;
	}
	
		
	public int fetchCompanyId(String companyName) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<CompanyDetails> companyDetails = null;
		int companyId = 0;
		try {

			CompanyDAO companyDao = new CompanyDAO();
			companyId = companyDao.fetchCompanyId(companyName);
			if(companyId == 0) {
				companyDetails = displayCompanies();
				throw new CompanyNotFoundException();
			}
		} catch (CompanyNotFoundException e) {
			e.setErrorData(companyDetails);
			throw e;
		}catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return companyId;
	}
	
	
	public String fetchCompanyName(int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		String companyName="";
		try {

			CompanyDAO companyDao = new CompanyDAO();
			companyName = companyDao.fetchCompanyName(companyId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return companyName;
	}

	public ArrayList<CompanyDetails> retrieveCompanyDetails(int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		
		ArrayList<CompanyDetails> comp = new ArrayList<CompanyDetails>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany(companyId);
			
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return comp;
	}
	
	
	public Company retrieveVacancyByCompany(int companyId,int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<CompanyDetails> comp = new ArrayList<CompanyDetails>();
		ArrayList<JobVacancy> vacancyDetails = null;
		Company company = new Company();
		try {

			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany(companyId);
			vacancyDetails = retrieveVacancyByCompany1(companyId, userId);
			company.setCompanyDetails(comp);
			company.setJobVacancy(vacancyDetails);
			
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return company;
	}

	
	public ArrayList<JobVacancy> retrieveVacancyByCompany1(int companyId,int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<JobVacancy> comp = new ArrayList<JobVacancy>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany1(companyId,userId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return comp;
	}

	public Company retrieveVacancyByCompanyAdmin(int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		//ArrayList<Company> companies = new ArrayList<Company>();
		Company company = new Company();
		ArrayList<JobVacancy> vacancies = null;
		ArrayList<JobMapping> job = null;
		try {
			
			CompanyDAO companyDao = new CompanyDAO();
			JobDelegate jobDelegate = new JobDelegate();
			vacancies = companyDao.retrieveVacancyByCompanyAdmin(companyId);
			job = jobDelegate.displayJobs();
			company.setJobVacancy(vacancies);
			company.setJobs(job);
			//companies.add(company);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return company;
	}

	public int numberOfVacancyPublished(int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		int count=0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			count = companyDao.numberOfVacancyPublished(companyId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return count;
	}

	public ArrayList<JobVacancy> retrieveVacancyByLocation(String location,int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<JobVacancy> comp = new ArrayList<JobVacancy>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByLocation(location,userId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return comp;
	}


	public boolean addNewCompany(CompanyDetails company) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		ArrayList<CompanyDetails> companyDetails = null;
		try {
			if(ifCompanyAlreadyExists(company.getCompanyName())) {
				companyDetails = displayCompanies();
				throw 	new CompanyAlreadyExistsException();
			}else {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.addNewCompany(company);
			}
		}
		catch (CompanyAlreadyExistsException e) {
			 e.setErrorData(companyDetails);
			throw e;
		}
		catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}


	private boolean ifCompanyAlreadyExists(String companyName)throws ApplicationException {
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.ifCompanyAlreadyExists(companyName);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}


	public boolean publishVacancy(int userId,int companyId,JobVacancy jobVacancy) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			User user = new User();
			user.setUserId(userId);
			jobVacancy.setCompanyId(companyId);
			if(ifVacancyAlreadyPublished(jobVacancy,user)) {
				throw new VacancyAlreadyPublishedException();
			}
			else {
			flag = companyDao.publishVacancy(jobVacancy, user);
			}
		}catch (VacancyAlreadyPublishedException e) {
			throw e;
		} 	
		catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	private boolean ifVacancyAlreadyPublished(JobVacancy jobVacancy, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.ifVacancyAlreadyPublished(jobVacancy,user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}


	public void compareVacancyWithRequest(int jobId,String location) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			JobVacancy company = new JobVacancy();
			company.setJobId(jobId);
			company.setLocation(location);
			CompanyDAO companyDao = new CompanyDAO();
			companyDao.compareVacancyWithRequest(company);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}

	public boolean removeVacancy(int companyId,String location,int userId,int jobId) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.removeVacancy(companyId,location,userId,jobId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}


	public boolean updateVacancyJobId(JobVacancy jobVacancy, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyJobId(jobVacancy, user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean updateVacancyLocation(JobVacancy jobVacancy, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyLocation(jobVacancy, user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean updateVacancyDescription(JobVacancy jobVacancy, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyDescription(jobVacancy, user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean updateVacancySalary(JobVacancy jobVacancy, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancySalary(jobVacancy, user);	
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean updateVacancyCount(JobVacancy jobVacancy, User user) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyCount(jobVacancy, user);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public void insertIntoCompanyDetails(int userId, int companyId) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			CompanyDAO companyDao = new CompanyDAO();
			companyDao.insertIntoCompanyDetails(userId, companyId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
	}


	public ArrayList<ApplyJob> viewAppliedUsers(int companyId)throws ApplicationException{
		// TODO Auto-generated method stub
		ArrayList<ApplyJob> comp = new ArrayList<ApplyJob>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.viewAppliedUsers(companyId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return comp;
	}
	
	public int numberOfAppliedUsers(int companyId)throws ApplicationException{
		// TODO Auto-generated method stub
		int count=0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			count = companyDao.numberOfAppliedUsers(companyId);
		} catch (Exception e) {
			throw new ApplicationException("EXCEPTION","Exception");
		}
		return count;
	}


	public ArrayList<ApplyJob> viewAppliedJobs(int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<ApplyJob> comp = new ArrayList<ApplyJob>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.viewAppliedJobs(userId);
		} catch (Exception e) {
			throw new ApplicationException("EXCEPTION","Exception");
		}
		return comp;
	}

}
