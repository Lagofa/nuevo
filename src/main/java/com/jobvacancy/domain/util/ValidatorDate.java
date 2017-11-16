package com.jobvacancy.domain.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.jobvacancy.domain.exception.DateException;

public class ValidatorDate {
	

	Date today=Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	
	public Date validateEndDate(Date startDate, Date endDate) throws DateException{
		if(!((today.before(endDate) || today.equals(endDate)) && (startDate.before(endDate) || startDate.equals(endDate)))){
        	throw new DateException("Invalid endDate");
        }
		return endDate;
	}	

}
