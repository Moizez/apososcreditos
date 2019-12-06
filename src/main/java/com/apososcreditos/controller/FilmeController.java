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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apososcreditos.model.Commentary;
import com.apososcreditos.model.Diretor;
import com.apososcreditos.model.Filme;
import com.apososcreditos.model.Genero;
import com.apososcreditos.model.Produtora;
import com.apososcreditos.service.CommentaryService;
import com.apososcreditos.service.DiretorService;
import com.apososcreditos.service.FilmeService;
import com.apososcreditos.service.GeneroService;
import com.apososcreditos.service.ProdutoraService;

@Controller
@RequestMapping("/filme")
public class FilmeController {

	@Autowired
	private FilmeService filmeService;

	@Autowired
	private GeneroService generoService;
	@Autowired
	private CommentaryService commentaryService;
	@Autowired
	private ProdutoraService produtoraService;
	@Autowired
	private DiretorService diretorService;

	@GetMapping("/add")
	public ModelAndView add(Filme filme) {

		ModelAndView mv = new ModelAndView("filme/form-api");
		mv.addObject("generos", generoService.findAll());
		mv.addObject("produtoras", produtoraService.findAll());
		mv.addObject("filme", filme);

		return mv;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Filme filme, @RequestParam("file") MultipartFile file, @RequestParam("genero") String genero,
			@RequestParam("produtoraApi") String produtora, 
			@RequestParam("diretorApi") String diretor, RedirectAttributes attr,
			BindingResult result) throws IOException {
		if (result.hasErrors()) {
			return add(filme);
		}

		if (!file.isEmpty()) {
			filme.setImagem(file.getBytes());
		}
		
		Diretor diretor2 = new Diretor();
		diretor2.setNome(diretor);
		diretorService.save(diretor2);
		
		Produtora produtora2 = new Produtora();
		produtora2.setNome(produtora);
		produtoraService.save(produtora2);
		
		String arrayGeneros[] = genero.split(",");
		for(int i = 0; i < arrayGeneros.length;i++) {
			if(!arrayGeneros[i].isEmpty()) {
				String generoName = arrayGeneros[i];
				Genero genero2 = new Genero();
				genero2.setNome(generoName);
				generoService.save(genero2);
				filme.getGeneros().add(genero2);
			}
		}
		filme.setDiretor(diretor2);
		filme.setProdutora(produtora2);
		filmeService.save(filme);
		attr.addFlashAttribute("success", "Filme inserido com sucesso.");
		return findAll();
	}

	@GetMapping("/listar")
	public ModelAndView findAll() {

		ModelAndView mv = new ModelAndView("filme/listar");
		mv.addObject("filmes", filmeService.findAll());

		return mv;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {

		return add(filmeService.findOne(id));
	}

	@GetMapping("/details/{id}")
	public ModelAndView details(@PathVariable("id") Long id) {

		ModelAndView mv = new ModelAndView("filme/detalhes");
		mv.addObject("filme", filmeService.findOne(id));

		return mv;
	}

	@GetMapping("/single-page/{id}")
	public ModelAndView critica(@PathVariable("id") Long id) {

		ModelAndView mv = new ModelAndView("filme/single-page");
		mv.addObject("filme", filmeService.findOne(id)).addObject("commentary", new Commentary())
				.addObject("commentars", commentaryService.findByCommentarsLinkedFilme(id));
		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {

		filmeService.delete(id);

		return findAll();
	}

	// EXIBIR IMAGEM
	@RequestMapping(path = { "/imagem/{id}" }, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImagem(@PathVariable("id") Long id) {
		Filme filme = filmeService.findOne(id);
		byte[] imagem = filme.getImagem();
		final org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(imagem, headers, HttpStatus.OK);
	}

	// MÉTODO DE BUSCA = FILMES POR NOME
	@GetMapping("/buscar/titulo")
	public ModelAndView findByFilme(@RequestParam("titulo") String titulo) {
		ModelAndView mv = new ModelAndView("/home");
		mv.addObject("filmes", filmeService.findByTitulo(titulo));
		return mv;
	}

}
