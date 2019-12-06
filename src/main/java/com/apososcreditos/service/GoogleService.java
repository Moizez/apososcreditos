package com.apososcreditos.service;

import org.springframework.social.google.api.plus.Person;

public interface GoogleService {

	String googlelogin();

	String getGoogleAccessToken(String code);

	Person getGoogleUserProfile(String accessToken);

}
