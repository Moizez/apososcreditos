package com.apososcreditos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apososcreditos.model.Genero;
import com.apososcreditos.service.GeneroService;

@Controller
@RequestMapping("/genero")
public class GeneroController {

	@Autowired
	private GeneroService generoService;

	@RequestMapping("/add")
	public ModelAndView add(Genero genero) {
		ModelAndView mv = new ModelAndView("genero/form");
		mv.addObject("genero", genero);
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Genero genero, BindingResult result) {

		if (result.hasErrors()) {
			return add(genero);
		}

		generoService.save(genero);

		return findAll();
	}

	@GetMapping("/listar")
	private ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("genero/listar");
		mv.addObject("generos", generoService.findAll());

		return mv;
	}

	@GetMapping("/edit/{id}")
	private ModelAndView edit(@PathVariable("id") Long id) {
		Genero gen = generoService.findOne(id);
		return add(gen);
	}

	@GetMapping("/delete/{id}")
	private ModelAndView delete(@PathVariable("id") Long id) {
		generoService.delete(id);
		return findAll();
	}

}
