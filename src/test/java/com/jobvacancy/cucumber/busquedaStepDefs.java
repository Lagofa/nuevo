package com.jobvacancy.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Given;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class busquedaStepDefs {
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

    //private MockMvc restJobOfferMockMvc;

    //private JobOffer jobOffer;


    @Before
    public void setup() {
    	 MockitoAnnotations.initMocks(this);
         JobOfferResource jobOfferResource = new JobOfferResource();
         ReflectionTestUtils.setField(jobOfferResource, "jobOfferRepository", jobOfferRepository);

         
         Optional<User> user =  userRepository.findOneByLogin("user");
         when(mockUserRepository.findOneByLogin(Mockito.any())).thenReturn(user);

         ReflectionTestUtils.setField(jobOfferResource, "userRepository", mockUserRepository);
      /*   this.restJobOfferMockMvc = MockMvcBuilders.standaloneSetup(jobOfferResource)
             .setCustomArgumentResolvers(pageableArgumentResolver)
             .setMessageConverters(jacksonMessageConverter).build();*/
    }

    @Before
    public void initTest() {
        //jobOffer = new JobOffer();
    }

	@Given("^​existe una oferta de \"([^\"]*)\" and existe una oferta \"([^\"]*)\"​$")
	public void existe_una_oferta_de_And_existe_una_oferta_​(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@When("^​busco \"([^\"]*)\"$")
	public void busco(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("^​encuentro la busqueda \"([^\"]*)\" and​ ​no encu​entro la busqueda \"([^\"]*)\"$")
	public void encuentro_la_busqueda_And​_​no_encu​entro_la_busqueda(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("^​encuentro la busqueda \"([^\"]*)\" and​ ​ encu​entro la busqueda \"([^\"]*)\"$")
	public void encuentro_la_busqueda_And​_​_encu​entro_la_busqueda(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}
	@Then("^​encuentro la busqueda \"([^\"]*)\" and​ ​encu​entro la busqueda \"([^\"]*)\"$")
	public void encuentro_la_busqueda_and​_​encu​entro_la_busqueda(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("^​encuentro la busqueda \"([^\"]*)\" and​ ​ no encu​entro la busqueda \"([^\"]*)\"$")
	public void encuentro_la_busqueda_and​_​_no_encu​entro_la_busqueda(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}


 
}
