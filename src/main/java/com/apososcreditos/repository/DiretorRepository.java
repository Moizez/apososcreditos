package com.apososcreditos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apososcreditos.model.Diretor;


@Repository
public interface DiretorRepository extends JpaRepository<Diretor, Long>{
	
	@Query
	public List<Diretor> findByNomeIgnoreCaseContaining(String nome);
}
