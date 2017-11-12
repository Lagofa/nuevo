package com.jobvacancy.web.rest.utils;

import org.junit.Assert;
import org.junit.Test;

import com.jobvacancy.domain.util.ValidatorJobApplicationData;

public class ValidatorJobAplicationDataTest {

	private ValidatorJobApplicationData validator = new ValidatorJobApplicationData();

	@Test
	public void validLinklTest() {
		Assert.assertTrue(this.validator.validate("https://www.linkedin.com/profile/view?id=AAMAABhp-"
				+ "akBJJDtLnfvsXb-3Ur2820svaUvhaU&trk=hp-identity-photo"));
	}

	@Test
	public void invalidLinklTest() {
		Assert.assertFalse(this.validator.validate("hs://www.linkedin.com/profile/view?id=AAMAABhp-"
				+ "akBJJDtLnfvsXb-3Ur2820svaUvhaU&trk=hp-identity-photo"));
	}
}

