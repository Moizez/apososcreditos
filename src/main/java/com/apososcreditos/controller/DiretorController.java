package com.apososcreditos.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.apososcreditos.model.Diretor;
import com.apososcreditos.service.DiretorService;

@Controller
@RequestMapping("/diretor")
public class DiretorController {
	
	@Autowired
	private DiretorService service;
	
	@GetMapping("/add")
	public ModelAndView add(Diretor diretor) {
		
		ModelAndView mv = new ModelAndView("diretor/form");
		mv.addObject("diretor", diretor);
		
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView save(@Valid Diretor diretor, @RequestParam("file")MultipartFile file, BindingResult result) throws IOException {
		
		if(result.hasErrors()) {
			return add(diretor);
		}
		
		if (!file.isEmpty()) {
			diretor.setImagem(file.getBytes());
		}
		
		service.save(diretor);
		
		return findAll();
	}
	
	@GetMapping("/listar")
	public ModelAndView findAll() {
		
		ModelAndView mv = new ModelAndView("diretor/listar");
		mv.addObject("diretores", service.findAll());
		
		return mv;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		
		return add(service.findOne(id));
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		
		service.delete(id);
		
		return findAll();
	}
	
	@RequestMapping(path = {"/imagem/{id}"}, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImagem(@PathVariable("id") Long id){
		Diretor diretor = service.findOne(id);
		byte[] imagem = diretor.getImagem();
		final org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(imagem, headers, HttpStatus.OK);
	}

}
