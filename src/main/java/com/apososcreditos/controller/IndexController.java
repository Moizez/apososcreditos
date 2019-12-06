package com.apososcreditos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apososcreditos.model.UserInfo;
import com.apososcreditos.service.FilmeService;
import com.apososcreditos.service.SessionService;

@Controller
public class IndexController {

	@Autowired
	private FilmeService filmeService;
	@Autowired
	private SessionService<UserInfo> sessionService;

	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView view = new ModelAndView("home");
		view.addObject("filmes", filmeService.getFilmeLimit(8));
		return view;
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserInfo());
		return "login/login_register_modal";
	}

	@GetMapping("/loginfailure")
	public String loginfailure(Model model) {
		model.addAttribute("user", new UserInfo());
		model.addAttribute("errormessage", "Verifique se seu e-mail ou senha estão corretos!");
		return "login/login_register_modal";
	}

	@GetMapping("/logout")
	public ModelAndView logout() {
		sessionService.clearSession();
		return home();
	}

	@RequestMapping("/adm")
	public String adm() {
		return "home-adm";
	}

}
