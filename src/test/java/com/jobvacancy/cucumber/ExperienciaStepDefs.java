package com.jobvacancy.cucumber;

import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jobvacancy.Application;
import com.jobvacancy.domain.JobOffer;
import com.jobvacancy.domain.User;
import com.jobvacancy.repository.JobOfferRepository;
import com.jobvacancy.repository.UserRepository;
import com.jobvacancy.web.rest.JobOfferResource;
import com.jobvacancy.web.rest.TestUtil;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Given;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class ExperienciaStepDefs {
	
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

    private JobOffer jobOffer;

    @Before
    public void setup() {
    	 MockitoAnnotations.initMocks(this);
         JobOfferResource jobOfferResource = new JobOfferResource();
         ReflectionTestUtils.setField(jobOfferResource, "jobOfferRepository", jobOfferRepository);

         
         Optional<User> user =  userRepository.findOneByLogin("user");
         when(mockUserRepository.findOneByLogin(Mockito.any())).thenReturn(user);

         ReflectionTestUtils.setField(jobOfferResource, "userRepository", mockUserRepository);
         this.restJobOfferMockMvc = MockMvcBuilders.standaloneSetup(jobOfferResource)
             .setCustomArgumentResolvers(pageableArgumentResolver)
             .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jobOffer = new JobOffer();
    }

	@Given("^el oferente llena el campos tittle and llena el campo description$")
	public void el_oferente_llena_el_campos_tittle_and_llena_el_campo_description() throws Throwable {
	    jobOffer.setTitle("java");
	    jobOffer.setDescription("Programador java");
	}

	@When("^ingresa (\\d+) como experiencia requerida and save nueva oferta de trabajo$")
	public void ingresa_como_experiencia_requerida_and_save_nueva_oferta_de_trabajo(int arg1) throws Throwable {
		jobOffer.setExperiencia(new Long(arg1));
		restJobOfferMockMvc.perform(post("/api/jobOffers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
                .andExpect(status().isCreated());
	}

	@Then("^se crea una oferta con (\\d+) como experiencia requerida$")
	public void se_crea_una_oferta_con_como_experiencia_requerida(int arg1) throws Throwable {
		 List<JobOffer> jobOffers = jobOfferRepository.findAll();
		 assertThat(jobOffers.get(0).getExperiencia()).isEqualTo(new Long(arg1));
		 jobOfferRepository.deleteAll();           
	}
	
	@When("^save nueva oferta de trabajo$")
	public void save_nueva_oferta_de_trabajo() throws Throwable {
		jobOffer.setExperiencia(null);
		restJobOfferMockMvc.perform(post("/api/jobOffers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
                .andExpect(status().isCreated());
	}

}
