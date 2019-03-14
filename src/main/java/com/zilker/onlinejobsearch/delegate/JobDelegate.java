package com.zilker.onlinejobsearch.delegate;


import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobVacancy;
import com.zilker.onlinejobsearch.constants.ErrorCodes;
import com.zilker.onlinejobsearch.customException.ApplicationException;
import com.zilker.onlinejobsearch.customException.JobDesignationAlreadyExistsException;
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

		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
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
		} 
		catch (JobDesignationNotFoundException e) {
			throw e;
		}catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return jobId;
	}
	
	public ArrayList<JobVacancy> retrieveVacancyByJob1(int jobId,int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<JobVacancy> comp = new ArrayList<JobVacancy>();
		try {
			JobDAO jobDao = new JobDAO();
			comp = jobDao.retrieveVacancyByJob1(jobId,userId);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return comp;
	}

	public boolean addNewJob(JobMapping jobMapping, int userId) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList<JobMapping> job = null;
		boolean flag = false ;
		try {
			if(ifJobDesignationAlreadyExists(jobMapping.getJobRole())) {	
				job = displayJobs();
				throw new JobDesignationAlreadyExistsException();
			}
			else {
			JobDAO jobDao = new JobDAO();
			flag = jobDao.addNewJob(jobMapping, userId);
			}
		} catch (JobDesignationAlreadyExistsException e) {
			 e.setErrorData(job);
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

	private boolean ifJobDesignationAlreadyExists(String jobRole)throws ApplicationException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			JobDAO jobDao = new JobDAO();
			flag = jobDao.ifJobDesignationAlreadyExists(jobRole);
		} catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	public boolean ifJobIdExists(JobMapping jobmapping) throws ApplicationException{
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			JobDAO jobDao = new JobDAO();
			flag = jobDao.ifTechnologyIdExists(jobmapping);
		}catch (SQLException e) {
			throw new ApplicationException(ErrorCodes.SQLERRORCODE,ErrorCodes.SQLERRORMESSAGE);
		} 
		catch (Exception e) {
			throw new ApplicationException(ErrorCodes.GERNERICERRORCODE,ErrorCodes.GERNERICERRORMESSAGE);
		}
		return flag;
	}

	
	
}

