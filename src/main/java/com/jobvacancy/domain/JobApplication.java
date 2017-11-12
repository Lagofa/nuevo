package com.jobvacancy.domain;

import com.jobvacancy.domain.error.LinkCVException;
import com.jobvacancy.domain.util.ValidatorJobApplicationData;

public class JobApplication {
	private ValidatorJobApplicationData validator = new ValidatorJobApplicationData();
	
	private Long offerId;

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long id) {
        this.offerId = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    private String fullname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    
    public String getlink_CV() {
        return link_CV;
    }

    public void setlink_CV(String link_CV) throws LinkCVException {
        if(this.validator.validate(link_CV)){
        	this.link_CV = link_CV;
        }else{
        	throw new LinkCVException("invalid link");
        }
    }

    private String link_CV;
}
