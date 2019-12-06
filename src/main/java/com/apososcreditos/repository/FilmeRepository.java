package com.apososcreditos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apososcreditos.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{
	
	@Query
	public List<Filme> findByTituloIgnoreCaseContaining(String titulo);

	@Query(value="select * from filme f order by f.id desc limit ?", nativeQuery=true)
	public List<Filme> findAllLimit(int limit);
	
	/*@Query("select year(f.ano) from filme f where year(f.ano) like %?1%;")
	public List<Filme> findByAnoIgnoreCaseContaining(String ano);*/
	
	
	
}
