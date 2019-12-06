package com.apososcreditos.service;

import com.apososcreditos.model.UserInfo;

public interface UserService {

	UserInfo save(UserInfo userInfo);

	UserInfo findByEmail(String email);
	
	public UserInfo getOne(Long id);

	void update(UserInfo userInfo);
}
