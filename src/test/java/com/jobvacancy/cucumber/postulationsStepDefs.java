package com.jobvacancy.cucumber;

import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
import com.jobvacancy.web.rest.JobOfferResource;
import com.jobvacancy.web.rest.TestUtil;
import com.jobvacancy.web.rest.dto.JobApplicationDTO;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Given;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class postulationsStepDefs {
	
	private static final String APPLICANT_FULLNAME = "THE APPLICANT";
    private static final String APPLICANT_EMAIL = "APPLICANT@TEST.COM";
    private static final String APPLICANT_VALID_LINK = "https://www.linkedin.com/profile/view?id=AAMAABhp-akBJJDtLnfvsXb-3Ur2820svaUvhaU&trk=hp-identity-name";
    private static final long OFFER_ID = 1;
    
    @Inject
    private UserRepository userRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Inject
    private JobOfferRepository jobOfferRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobOfferMockMvc;
    private MockMvc restMockMvc;
    private JobOffer jobOffer;
    @Mock
    private MailService mailService;

    private Date today;
    @Before
    public void setup() {
		
    	 MockitoAnnotations.initMocks(this);
         JobOfferResource jobOfferResource = new JobOfferResource();
         ReflectionTestUtils.setField(jobOfferResource, "jobOfferRepository", jobOfferRepository);

         Optional<User> user =  userRepository.findOneByLogin("user");
         jobOffer.setOwner(user.get());

         when(mockUserRepository.findOneByLogin(Mockito.any())).thenReturn(user);

         ReflectionTestUtils.setField(jobOfferResource, "userRepository", mockUserRepository);
         this.restJobOfferMockMvc = MockMvcBuilders.standaloneSetup(jobOfferResource)
             .setCustomArgumentResolvers(pageableArgumentResolver)
             .setMessageConverters(jacksonMessageConverter).build();
         
    }

    @Before
    public void initTest() {
    	today =Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	jobOffer = new JobOffer();
    }

	@Given("^el oferente ingreso todos los datos para crear la nueva jobOffer \"([^\"]*)\"$")
public void el_oferente_ingreso_todos_los_datos_para_crear_la_nueva_jobOffer(String arg1) throws Throwable {
		jobOffer.setTitle(arg1);
		jobOffer.setStartDate(today);
		jobOffer.setEndDate(today);
	}

@When("^el oferente guarda la jobOffer$")
public void el_oferente_guarda_la_jobOffer() throws Throwable {
	restJobOfferMockMvc.perform(post("/api/jobOffers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isCreated());

   }

@Then("^la cantidad de postulaciones a la jobOffer \"([^\"]*)\" es (\\d+)$")
public void la_cantidad_de_postulaciones_a_la_jobOffer_es(String arg1, int arg2) throws Throwable {
	List<JobOffer>jobOffers = jobOfferRepository.findAll();
	assertThat(jobOffers.get(0).getTitle()).isEqualTo(arg1);
	assertThat(jobOffers.get(0).getPostulations()).isEqualTo(new Long(arg2));

}

@Given("^la cantidad de postulaciones de la jobOffer \"([^\"]*)\" es (\\d+)$")
public void la_cantidad_de_postulaciones_de_la_jobOffer_es(String arg1, int arg2) throws Throwable {
	List<JobOffer>jobOffers = jobOfferRepository.findAll();
	assertThat(jobOffers.get(0).getTitle()).isEqualTo(arg1);
	assertThat(jobOffers.get(0).getPostulations()).isEqualTo(new Long(arg2));
}

@When("^un usuario se postula a la oferta \"([^\"]*)\"$")
public void un_usuario_se_postula_a_la_oferta(String arg1) throws Throwable {
}

}
