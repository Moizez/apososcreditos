package com.apososcreditos.controller;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.apososcreditos.model.UserInfo;
import com.apososcreditos.service.FacebookService;
import com.apososcreditos.service.SecurityService;
import com.apososcreditos.service.SessionService;
import com.apososcreditos.service.UserService;

@Controller
public class FacebookController {

	@Autowired
	private FacebookService facebookService;
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService<UserInfo> sessionService;
	@Autowired
	private SecurityService securityService;

	@GetMapping(value = "/facebooklogin")
	public RedirectView facebooklogin() {
		RedirectView redirectView = new RedirectView();
		String url = facebookService.facebooklogin();
		System.out.println(url);
		redirectView.setUrl(url);

		return redirectView;
	}

	@GetMapping(value = "/facebook")
	public String facebook(@RequestParam("code") String code) {
		String accessToken = facebookService.getFacebookAccessToken(code);
		return "redirect:/facebookprofiledata/" + accessToken;

	}

	@GetMapping(value = "/facebookprofiledata/{accessToken:.+}")
	public String facebookprofiledata(@PathVariable String accessToken, Model model, HttpServletRequest request) {
		User user = facebookService.getFacebookUserProfile(accessToken);
		UserInfo dbUser = userService.findByEmail(user.getEmail());
		String role = "USER";
		if (dbUser != null) {
			dbUser.setFirstName(user.getFirstName());
			dbUser.setLastName(user.getLastName());
			//dbUser.setImage(user.getCover().getSource());
			userService.update(dbUser);
			role = dbUser.getRole();
			model.addAttribute("user", dbUser);
		} else {
			UserInfo userInfo = new UserInfo(user.getFirstName(), user.getLastName(), "");
			userInfo.setEmail(user.getEmail());
			userInfo.setEnabled(true);
			userInfo.setRole("USER");
			userService.save(userInfo);
			role = userInfo.getRole();
			model.addAttribute("user", userInfo);

		}
		System.out.println("PASSOU AQUI");
		sessionService.criarSession("usuario-logado", dbUser);
		securityService.autoLogin(user.getEmail(), null, role, request);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		System.out.println(name);
		return "redirect:/redirectdashboard";
	}

}
