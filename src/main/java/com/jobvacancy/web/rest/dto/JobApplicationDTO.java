package com.jobvacancy.web.rest.dto;

/**
 * Created by nicopaez on 10/11/15.
 */
public class JobApplicationDTO {
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
    
    public String getLink_CV() {
        return link_CV;
    }

    public void setLink_CV(String link_CV) {
        this.link_CV = link_CV;
    }

    private String link_CV;
}
