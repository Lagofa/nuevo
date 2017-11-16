
package com.jobvacancy.web.rest.utils;

import com.jobvacancy.domain.JobOffer;
import com.jobvacancy.domain.exception.DateException;


public class ValidatorJobOffer {
	
	public  JobOffer validateJobOffer(JobOffer jobOffer){
		JobOffer jobOffeNew= new JobOffer();
			jobOffeNew.setStartDate(jobOffer.getStartDate());
		try {
			jobOffeNew.setEndDate(jobOffer.getEndDate());
		} catch (DateException e) {
			return jobOffeNew;
		}
		jobOffeNew.setTitle(jobOffer.getTitle());
		jobOffeNew.setDescription(jobOffer.getDescription());
		jobOffeNew.setExperiencia(jobOffer.getExperiencia());
		jobOffeNew.setId(jobOffer.getId());
		jobOffeNew.setLocation(jobOffer.getLocation());
		jobOffeNew.setOwner(jobOffer.getOwner());
		return jobOffeNew;
	}

}
