package com.jobvacancy.web.rest.util;
import com.jobvacancy.domain.JobOffer;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Search {
	
	public List<JobOffer> search(List<JobOffer> allJobs, String word){
		Iterator<JobOffer> it =allJobs.iterator();
		List<JobOffer> searchJobs = new LinkedList<JobOffer>();
		while(it.hasNext()){
			JobOffer job=it.next();
			if(job.getDescription().contains(word)){
    			searchJobs.add(job);
			}
		}
		
		return searchJobs;
		
	}

}
