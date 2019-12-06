package com.apososcreditos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apososcreditos.model.Role;
import com.apososcreditos.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public void add(Role role) {
		roleRepository.saveAndFlush(role);
	}
	
	public Role getNome(String name) {
		return roleRepository.findByName(name);
	}
	
	public List<Role> buscarTodos(){
		return roleRepository.findAll();
	}

}
