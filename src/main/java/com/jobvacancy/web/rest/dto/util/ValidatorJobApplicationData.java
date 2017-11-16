package com.jobvacancy.web.rest.dto.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorJobApplicationData {

	 private final String patternLink =  "^(ftp|http|https)://?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";
	 
	    public boolean validate(String string) {
	    	Pattern pattern=null;
	        	pattern = Pattern.compile(this.patternLink);
	        Matcher matcher = pattern.matcher(string);
	        return matcher.matches();
	    }
}