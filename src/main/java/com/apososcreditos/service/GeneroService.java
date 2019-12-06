package com.apososcreditos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apososcreditos.model.Genero;
import com.apososcreditos.repository.GeneroRepository;

@Service
public class GeneroService {
	
	@Autowired
	private GeneroRepository repository;

	public void save(Genero genero) {
        repository.saveAndFlush(genero);
    }
	
	public List<Genero> findAll(){
		return repository.findAll();
	}
	
	public Genero findOne(Long id) {
        return repository.getOne(id);
    }
     
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Genero> findByNome(String nome) {
		return repository.findByNomeIgnoreCaseContaining(nome);
	}    

}
