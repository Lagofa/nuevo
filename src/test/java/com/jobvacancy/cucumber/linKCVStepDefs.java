package com.jobvacancy.cucumber;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jobvacancy.Application;
import com.jobvacancy.domain.JobOffer;
import com.jobvacancy.domain.User;
import com.jobvacancy.repository.JobOfferRepository;
import com.jobvacancy.repository.UserRepository;
import com.jobvacancy.service.MailService;
import com.jobvacancy.web.rest.JobApplicationResource;
import com.jobvacancy.web.rest.TestUtil;
import com.jobvacancy.web.rest.UserResource;
import com.jobvacancy.web.rest.dto.JobApplicationDTO;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class linKCVStepDefs {
	private static final String APPLICANT_FULLNAME = "THE APPLICANT";
    private static final String APPLICANT_EMAIL = "APPLICANT@TEST.COM";
    private static final String APPLICANT_VALID_LINK = "https://www.linkedin.com/profile/view?id=AAMAABhp-akBJJDtLnfvsXb-3Ur2820svaUvhaU&trk=hp-identity-name";
    private static final String APPLICANT_INVALID_LINK = "htts://www.linkedin.com/profile/view?id=AAMAABhp-akBJJDtLnfvsXb-3Ur2820svaUvhaU&trk=hp-identity-name";
    private MockMvc restMockMvc;

    private static final long OFFER_ID = 1;
    private static final String OFFER_TITLE = "SAMPLE_TEXT";

    @Mock
    private MailService mailService;

    @Mock
    private JobOfferRepository jobOfferRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private JobOffer offer;
    private JobApplicationDTO dto;
    private ResultActions actions;
    
    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Optional<User> user = userRepository.findOneByLogin("user");
        offer = new JobOffer();
        offer.setTitle(OFFER_TITLE);
        offer.setId(OFFER_ID);
        offer.setOwner(user.get());
        dto = new JobApplicationDTO();
        when(jobOfferRepository.findOne(OFFER_ID)).thenReturn(offer);
        JobApplicationResource jobApplicationResource = new JobApplicationResource();
        ReflectionTestUtils.setField(jobApplicationResource, "jobOfferRepository", jobOfferRepository);
        ReflectionTestUtils.setField(jobApplicationResource, "mailService", mailService);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(jobApplicationResource)
            .setMessageConverters(jacksonMessageConverter).build();
    }

	@Given("^el postulante ha ingresado su nombre y un mail$")
	public void el_postulante_ha_ingresado_su_nombre_y_un_mail() throws Throwable {
		dto.setEmail(APPLICANT_EMAIL);
        dto.setFullname(APPLICANT_FULLNAME);
	}
	@When("^el postulante ingresa un link valido \"([^\"]*)\" a su CV and se postula para la jobOffer$")
	public void el_postulante_ingresa_un_link_valido_a_su_CV_and_se_postula_para_la_jobOffer(String arg1) throws Throwable {
		dto.setOfferId(OFFER_ID);
        dto.setLink_CV(arg1);
        when(mailService.sendApplication(APPLICANT_EMAIL, offer, arg1)).thenReturn(Boolean.TRUE);
        actions=restMockMvc.perform(post("/api/Application")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dto)));
	}

	@Then("^se envia mail al oferente de la jobOffer$")
	public void se_envia_mail_al_oferente_de_la_jobOffer() throws Throwable {
		actions.andExpect(status().isAccepted());	
	}

	@When("^el postulante ingresa un link invalido \"([^\"]*)\" a su CV and se postula para la jobOffer$")
	public void el_postulante_ingresa_un_link_invalido_a_su_CV_and_se_postula_para_la_jobOffer(String arg1) throws Throwable {
		dto.setOfferId(OFFER_ID);
        dto.setLink_CV(arg1);
        when(mailService.sendApplication(APPLICANT_EMAIL, offer, arg1)).thenReturn(Boolean.FALSE);
        actions=restMockMvc.perform(post("/api/Application")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dto)));
	}

	@Then("^no se envia mail al oferente de la jobOffer$")
	public void no_se_envia_mail_al_oferente_de_la_jobOffer() throws Throwable {
		actions.andExpect(status().isBadRequest());	
	}

	@When("^el postulante no ingresa un link a su CV and se postula para la jobOffer$")
	public void el_postulante_no_ingresa_un_link_a_su_CV_and_se_postula_para_la_jobOffer() throws Throwable {
		dto.setOfferId(OFFER_ID);
        dto.setLink_CV("");
        when(mailService.sendApplication(APPLICANT_EMAIL, offer, "")).thenReturn(Boolean.FALSE);
        actions=restMockMvc.perform(post("/api/Application")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dto)));
	}
}
