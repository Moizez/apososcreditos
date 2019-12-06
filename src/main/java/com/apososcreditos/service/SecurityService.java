package com.apososcreditos.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

	void autoLogin(String email, String password, String role, HttpServletRequest request);

}
