package com.jobvacancy.cucumber;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jobvacancy.Application;
import com.jobvacancy.web.rest.UserResource;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class linKCVStepDefs {

	@Given("^el postulante ha ingresado su nombre y un mail$")
	public void el_postulante_ha_ingresado_su_nombre_y_un_mail() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}
	@When("^el postulante ingresa un link valido \"([^\"]*)\" a su CV and se postula para la jobOffer$")
	public void el_postulante_ingresa_un_link_valido_a_su_CV_and_se_postula_para_la_jobOffer(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("^se envia mail al oferente de la jobOffer$")
	public void se_envia_mail_al_oferente_de_la_jobOffer() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@When("^el postulante ingresa un link invalido \"([^\"]*)\" a su CV and se postula para la jobOffer$")
	public void el_postulante_ingresa_un_link_invalido_a_su_CV_and_se_postula_para_la_jobOffer(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("^no se envia mail al oferente de la jobOffer$")
	public void no_se_envia_mail_al_oferente_de_la_jobOffer() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}

	@When("^el postulante no ingresa un link a su CV and se postula para la jobOffer$")
	public void el_postulante_no_ingresa_un_link_a_su_CV_and_se_postula_para_la_jobOffer() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	}
}
