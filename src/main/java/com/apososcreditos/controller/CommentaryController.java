package com.apososcreditos.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.apososcreditos.model.Commentary;
import com.apososcreditos.model.Filme;
import com.apososcreditos.model.UserInfo;
import com.apososcreditos.service.CommentaryService;
import com.apososcreditos.service.FilmeService;
import com.apososcreditos.service.SessionService;
import com.apososcreditos.service.UserServiceImpl;

@Controller
@RequestMapping("/commentary")
public class CommentaryController {
	@Autowired
	private FilmeService filmeService;
	@Autowired
	private CommentaryService commentaryService;
	@Autowired
	private UserServiceImpl serviceImpl;
	@Autowired
	private SessionService<UserInfo> sessionService;

	@GetMapping("/add")
	public ModelAndView add(Commentary commentary) {

		ModelAndView mv = new ModelAndView("filme/single-page");
		mv.addObject("commentary", commentary);
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Commentary commentary, BindingResult result,
			@RequestParam("idFilme") Long idFilme) {
		UserInfo userInfoSession = sessionService.getSession("usuario-logado");
		Filme filme = filmeService.findOne(idFilme);
		if (userInfoSession != null) {
			UserInfo userInfo = serviceImpl.getOne(userInfoSession.getId());

			if (result.hasErrors()) {
				return add(commentary);
			}
			commentary.setFilme(filme);
			commentary.setUserInfo(userInfo);
			commentary.setData(new Date());
			commentaryService.save(commentary);

			return new ModelAndView("filme/single-page").addObject("filme", filme)
					.addObject("success", "Comentário realizado!").addObject("commentary", new Commentary())
					.addObject("commentars", commentaryService.findByCommentarsLinkedFilme(filme.getId()));
		}

		return new ModelAndView("filme/single-page").addObject("filme", filme)
				.addObject("error", "Efetue o login para realizar um comentário")
				.addObject("commentary", new Commentary())
				.addObject("commentars", commentaryService.findByCommentarsLinkedFilme(filme.getId()));

	}

	@GetMapping("/listar")
	public ModelAndView findAll() {

		ModelAndView mv = new ModelAndView("filme/single-page");
		mv.addObject("commentaries", commentaryService.findAll());

		return mv;
	}
	
	@GetMapping("/listar_adm")
	public ModelAndView findAllAdm() {

		ModelAndView mv = new ModelAndView("commentary/listar_adm");
		mv.addObject("commentaries", commentaryService.findAll());

		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {

		commentaryService.delete(id);

		return findAllAdm();
	}

}
