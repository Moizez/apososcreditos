package com.apososcreditos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apososcreditos.model.Produtora;
import com.apososcreditos.repository.ProdutoraRepository;

@Service
public class ProdutoraService {
	
	@Autowired
	private ProdutoraRepository repository;
	
	public Produtora save(Produtora produtora) {
        return repository.saveAndFlush(produtora);
    }

	public List<Produtora> findAll(){
		return repository.findAll();
	}
	
	public Produtora findOne(Long id) {
        return repository.getOne(id);
    }
     
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
