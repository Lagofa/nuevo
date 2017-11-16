package com.jobvacancy.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jobvacancy.Application;
import com.jobvacancy.domain.JobOffer;
import com.jobvacancy.domain.User;
import com.jobvacancy.domain.exception.DateException;
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
public class US5StepDefs {
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
    private Date today;
    private DateException exception;
    
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
        today =Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

@Given("^Titulo de la oferta \"([^\"]*)\"$")
public void titulo_de_la_oferta(String arg1) throws Throwable {
    jobOffer.setTitle(arg1);
}

@When("^ingreso fecha de publicacion (\\d+)  dias despues de hoy and fecha vencimiento (\\d+) dias despues de hoy and guardo la oferta$")
public void ingreso_fecha_de_publicacion_dias_despues_de_hoy_and_fecha_vencimiento_dias_despues_de_hoy_and_guardo_la_oferta(int arg1, int arg2) throws Throwable {
	Date startDate=new Date(today.getTime() + TimeUnit.DAYS.toMillis(arg1));
	Date endDate=new Date(today.getTime() + TimeUnit.DAYS.toMillis(arg2));
	jobOffer.setStartDate(startDate);
	jobOffer.setEndDate(endDate);
	restJobOfferMockMvc.perform(post("/api/jobOffers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isCreated());
	
}

@Then("^se creo la oferta \"([^\"]*)\" con fecha de publicacion (\\d+) dias despues de hoy and fecha vencimiento (\\d+) dias despues de hoy$")
public void se_creo_la_oferta_con_fecha_de_publicacion_dias_despues_de_hoy_and_fecha_vencimiento_dias_despues_de_hoy(String arg1, int arg2, int arg3) throws Throwable {
	Date startDate=new Date(today.getTime() + TimeUnit.DAYS.toMillis(arg2));
	Date endDate=new Date(today.getTime() + TimeUnit.DAYS.toMillis(arg3));
	List<JobOffer> jobOffers = jobOfferRepository.findAll();
	 assertThat(jobOffers.get(0).getTitle()).isEqualTo(arg1);
	 assertThat(jobOffers.get(0).getStartDate()).isEqualTo(startDate);
	 assertThat(jobOffers.get(0).getEndDate()).isEqualTo(endDate);
	 jobOfferRepository.deleteAll();
}


@Then("^no se creo la oferta con enDate$")
public void no_se_creo_la_oferta_con_endDate() throws Throwable {
	assertThat(exception.getMessage()).isEqualTo("Invalid endDate");
 }

@When("^ingreso fecha de vencimiento (\\d+) dias antes de hoy y fecha de publicacion hoy$")
public void ingreso_fecha_de_vencimiento_dias_antes_de_hoy_y_fecha_de_publicacion_hoy(int arg1) throws Throwable {
	Date startDate=today;
	Date endDate=new Date(today.getTime() - TimeUnit.DAYS.toMillis(arg1));
	jobOffer.setStartDate(startDate);
	try{
		jobOffer.setEndDate(endDate);
	}catch(DateException e){
		 exception=e;
	}
}

@Then("^no se creo la oferta con endDate menor a starDate$")
public void no_se_creo_la_oferta_con_endDate_menor_a_starDate() throws Throwable {
	assertThat(exception.getMessage()).isEqualTo("Invalid endDate");
}

@When("^ingreso fecha vencimiento (\\d+) dias antes de hoy$")
public void ingreso_fecha_vencimiento_dias_antes_de_hoy(int arg1) throws Throwable {
	Date endDate=new Date(today.getTime() - TimeUnit.DAYS.toMillis(arg1));
	try{
		jobOffer.setEndDate(endDate);
	}catch(DateException e){
		 exception=e;
	}
}

}
