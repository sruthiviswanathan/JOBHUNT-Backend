package com.zilker.onlinejobsearch.delegate;


import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobVacancy;
import com.zilker.onlinejobsearch.customException.ApplicationException;
import com.zilker.onlinejobsearch.customException.JobDesignationNotFoundException;
import com.zilker.onlinejobsearch.dao.JobDAO;

@Service
public class JobDelegate {

	public ArrayList<JobMapping> displayJobs() throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<JobMapping> job = new ArrayList<JobMapping>();
		try {
			JobDAO jobDao = new JobDAO();
			job = jobDao.displayJobs();

		} catch (Exception e) {
			throw new ApplicationException("EXCEPTION","Exception");
		}
		return job;
	}

	public int fetchJobId(String jobDesignation) throws ApplicationException {
		// TODO Auto-generated method stub
		int jobId = 0;
		try {
			JobDAO jobDao = new JobDAO();
			jobId = jobDao.fetchJobId(jobDesignation);
			if(jobId == 0) {
				throw new JobDesignationNotFoundException();
			}
			return jobId;
		} 
		catch (JobDesignationNotFoundException e) {
			throw e;
		}catch (SQLException e) {
			throw new ApplicationException("SQL","SQL Exception");
		}
		catch(Exception e) {
			throw new ApplicationException("EXCEPTION","Exception");
		}
	}
	
	public ArrayList<JobVacancy> retrieveVacancyByJob1(int jobId,int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<JobVacancy> comp = new ArrayList<JobVacancy>();
		try {
			JobDAO jobDao = new JobDAO();
			comp = jobDao.retrieveVacancyByJob1(jobId,userId);
		} catch (SQLException e) {
			throw new ApplicationException("SQL","SQL Exception");
		}
		catch(Exception e) {
			throw new ApplicationException("EXCEPTION","Exception");
		}
		return comp;
	}

	public boolean addNewJob(JobMapping jobMapping, int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false ;
		try {
			JobDAO jobDao = new JobDAO();
			flag = jobDao.addNewJob(jobMapping, userId);
			return flag;
		} catch (Exception e) {
			throw new ApplicationException("EXCEPTION","Exception");
		}

	}

	public boolean ifJobIdExists(JobMapping jobmapping) throws SQLException{
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			JobDAO jobDao = new JobDAO();
			flag = jobDao.ifTechnologyIdExists(jobmapping);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	
	
}

