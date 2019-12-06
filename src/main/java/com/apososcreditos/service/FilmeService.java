package com.apososcreditos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apososcreditos.model.Filme;
import com.apososcreditos.repository.FilmeRepository;

@Service
public class FilmeService {
	
	@Autowired
	private FilmeRepository repository;

	public Filme save(Filme filme) {
		return repository.saveAndFlush(filme);
	}

	public List<Filme> findAll() {
		return repository.findAll();
	}
	
	public Filme findOne(Long id) {
		return repository.getOne(id);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public List<Filme> findByTitulo(String titulo) {
		return repository.findByTituloIgnoreCaseContaining(titulo);
	}

	/*public List<Filme> findByAno(String ano) {
		return repository.findByAnoIgnoreCaseContaining(ano);
	}	*/
	
	public List<Filme> getFilmeLimit(int limit){
		return repository.findAllLimit(limit);
	}

}
