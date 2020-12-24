package com.nphc.hrmgmt.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties
public class ApplicationProperties {

	String msgLoginInvalid;
	String msgSalaryInvalid;
	
}
