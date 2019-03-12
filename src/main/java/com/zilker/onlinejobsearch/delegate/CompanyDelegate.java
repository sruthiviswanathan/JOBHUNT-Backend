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
import com.zilker.onlinejobsearch.dao.CompanyDAO;

@Service
public class CompanyDelegate {

	public ArrayList<CompanyDetails> displayCompanies() throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<CompanyDetails> comp = null;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.displayCompanies();
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}
	
		
	public int fetchCompanyId(String companyName) throws SQLException {
		// TODO Auto-generated method stub
		int companyId = 0;
		try {

			CompanyDAO companyDao = new CompanyDAO();
			companyId = companyDao.fetchCompanyId(companyName);
			return companyId;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	
	public String fetchCompanyName(int companyId) throws SQLException {
		// TODO Auto-generated method stub
		String companyName="";
		try {

			CompanyDAO companyDao = new CompanyDAO();
			companyName = companyDao.fetchCompanyName(companyId);
			return companyName;
		} catch (SQLException e) {
			throw e;
		}
	}

	public ArrayList<CompanyDetails> retrieveCompanyDetails(int companyId) throws SQLException {
		// TODO Auto-generated method stub
		
		ArrayList<CompanyDetails> comp = new ArrayList<CompanyDetails>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany(companyId);
			
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}
	
	
	public ArrayList<Company> retrieveVacancyByCompany(int companyId,int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> companies = new ArrayList<Company>();
		ArrayList<CompanyDetails> comp = new ArrayList<CompanyDetails>();
		ArrayList<JobVacancy> vacancyDetails = null;
		try {

			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany(companyId);
			vacancyDetails = retrieveVacancyByCompany1(companyId, userId);
			Company company = new Company();
			company.setCompanyDetails(comp);
			company.setJobVacancy(vacancyDetails);
			companies.add(company);
		} catch (SQLException e) {
			throw e;
		}
		return companies;
	}

//jobvacancy
	
	public ArrayList<JobVacancy> retrieveVacancyByCompany1(int companyId,int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<JobVacancy> comp = new ArrayList<JobVacancy>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany1(companyId,userId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}

	public ArrayList<Company> retrieveVacancyByCompanyAdmin(int companyId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> companies = new ArrayList<Company>();
		ArrayList<JobVacancy> vacancies = null;
		ArrayList<JobMapping> job = null;
		try {
			Company company = new Company();
			CompanyDAO companyDao = new CompanyDAO();
			JobDelegate jobDelegate = new JobDelegate();
			vacancies = companyDao.retrieveVacancyByCompanyAdmin(companyId);
			job = jobDelegate.displayJobs();
			company.setJobVacancy(vacancies);
			company.setJobs(job);
			companies.add(company);
		} catch (SQLException e) {
			throw e;
		}
		return companies;
	}

	public int numberOfVacancyPublished(int companyId) throws SQLException {
		// TODO Auto-generated method stub
		int count=0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			count = companyDao.numberOfVacancyPublished(companyId);
		} catch (SQLException e) {
			throw e;
		}
		return count;
	}

	public ArrayList<JobVacancy> retrieveVacancyByLocation(String location,int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<JobVacancy> comp = new ArrayList<JobVacancy>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByLocation(location,userId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}


	public boolean addNewCompany(CompanyDetails company) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.addNewCompany(company);
		} catch (SQLException e) {
			throw e;
		}

		return flag;
	}

//	public int addNewCompanyBySiteAdmin(Company company, User user) throws SQLException {
//		// TODO Auto-generated method stub
//		int flag = 0;
//		try {
//			CompanyDAO companyDao = new CompanyDAO();
//			flag = companyDao.addNewCompanyBySiteAdmin(company, user);
//		} catch (SQLException e) {
//			throw e;
//		}
//
//		return flag;
//	}

	public boolean publishVacancy(int userId,int companyId,JobVacancy jobVacancy) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			User user = new User();
			user.setUserId(userId);
			jobVacancy.setCompanyId(companyId);
			flag = companyDao.publishVacancy(jobVacancy, user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public void compareVacancyWithRequest(int jobId,String location) throws SQLException {
		// TODO Auto-generated method stub
		try {
			JobVacancy company = new JobVacancy();
			company.setJobId(jobId);
			company.setLocation(location);
			CompanyDAO companyDao = new CompanyDAO();
			companyDao.compareVacancyWithRequest(company);
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean removeVacancy(int companyId,int userId,int jobId) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.removeVacancy(companyId, userId,jobId);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}


	public boolean updateVacancyJobId(JobVacancy jobVacancy, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyJobId(jobVacancy, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancyLocation(JobVacancy jobVacancy, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyLocation(jobVacancy, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancyDescription(JobVacancy jobVacancy, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyDescription(jobVacancy, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancySalary(JobVacancy jobVacancy, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancySalary(jobVacancy, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancyCount(JobVacancy jobVacancy, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyCount(jobVacancy, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public void insertIntoCompanyDetails(int userId, int companyId) throws SQLException {
		// TODO Auto-generated method stub
		try {
			CompanyDAO companyDao = new CompanyDAO();
			companyDao.insertIntoCompanyDetails(userId, companyId);
		} catch (SQLException e) {
			throw e;
		}

	}


	public ArrayList<ApplyJob> viewAppliedUsers(int companyId)throws SQLException{
		// TODO Auto-generated method stub
		ArrayList<ApplyJob> comp = new ArrayList<ApplyJob>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.viewAppliedUsers(companyId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}
	
	public int numberOfAppliedUsers(int companyId)throws SQLException{
		// TODO Auto-generated method stub
		int count=0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			count = companyDao.numberOfAppliedUsers(companyId);
		} catch (SQLException e) {
			throw e;
		}
		return count;
	}


	public ArrayList<ApplyJob> viewAppliedJobs(int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<ApplyJob> comp = new ArrayList<ApplyJob>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.viewAppliedJobs(userId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}

}
