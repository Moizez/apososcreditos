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

import com.apososcreditos.model.Produtora;
import com.apososcreditos.service.ProdutoraService;

@Controller
@RequestMapping("/produtora")
public class ProdutoraController {
	
	@Autowired
	private ProdutoraService produtoraService;
	
	@RequestMapping("/add")
	public ModelAndView add(Produtora produtora) {
		ModelAndView mv = new ModelAndView("produtora/form");
		mv.addObject("produtora", produtora);
		return mv;
	}
	
	@PostMapping("/save")
    public ModelAndView save(@Valid Produtora produtora, BindingResult result) {
		 
		if(result.hasErrors()) {
			return add(produtora);
	    }

		produtoraService.save(produtora);			
				
		return findAll();
    }

	@GetMapping("/listar")
	private ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("produtora/listar");
        mv.addObject("produtoras", produtoraService.findAll());
        
        return mv;
	}
		
	@GetMapping("/edit/{id}")
	private ModelAndView edit( @PathVariable("id") Long id) {
		Produtora produtora = produtoraService.findOne(id);
		return add(produtora);
	}	

	@GetMapping("/delete/{id}")
	private ModelAndView delete( @PathVariable("id") Long id) {
		produtoraService.delete(id);
		return findAll();
	}

}
