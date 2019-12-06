package com.apososcreditos.service;

import org.springframework.social.facebook.api.User;

public interface LinkedInService {
	
	String facebooklogin();

	String getFacebookAccessToken(String code);

	User getFacebookUserProfile(String accessToken);

}
