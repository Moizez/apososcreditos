package com.apososcreditos.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.apososcreditos.model.Role;
import com.apososcreditos.model.UserInfo;
import com.apososcreditos.service.RoleService;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private RoleService serviceRole;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("----- Criando Usuário ------");
		createUserAdmin();
		// createUsuario();
		System.out.println("----- Usuário Criado com Sucesso! -----");
	}

	private void createUserAdmin() {
		UserInfo usuario = new UserInfo();
		usuario.setFirstName("Moisés");
		usuario.setLastName("Henrique");
		usuario.setEmail("moizez@gmail.com");
		usuario.setPassword("123");
		Role role = serviceRole.getNome("ADMIN");
		if(role == null) {
			role = new Role();
			role.setName("ADMIN");
			serviceRole.add(role);
			/*usuario.getRole().add(role);
			userService.createLinkAtivarConta(usuario); 
			serviceUsuario.add(usuario);
			Email email = new Email();
			email.setTo(usuario.getEmail());
			emailService.sendEmailBemVindo(email);*/
		}

	}
}
