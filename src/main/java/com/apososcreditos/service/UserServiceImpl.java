package com.apososcreditos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.apososcreditos.model.UserInfo;
import com.apososcreditos.repository.UserRepository;
import com.apososcreditos.util.PasswordUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserInfo save(UserInfo userInfo) {
		userInfo.setEnabled(true);
		if (StringUtils.hasText(userInfo.getPassword())) {
			userInfo.setPassword(PasswordUtil.getEncodePassword(userInfo.getPassword()));

		}

		return userRepository.save(userInfo);
	}

	@Override
	public UserInfo findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void update(UserInfo dbUser) {
		userRepository.save(dbUser);
	}

	@Override
	public UserInfo getOne(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public UserInfo findByRoleAdmin() {
		return userRepository.findByRoleAdmin();
	}

}
