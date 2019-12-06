package com.apososcreditos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apososcreditos.model.Commentary;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
	@Query(value="SELECT * FROM commentary c where c.id in(\n" + 
			"	select c2.id from commentary c2\n" + 
			"    inner join user u on(u.id = c2.comentario_user)\n" + 
			"    inner join filme f on(f.id = c2.comentario_filme)\n" + 
			"    where f.id = ?\n" + 
			");", nativeQuery = true)
	public List<Commentary> findByCommentarsLinkedFilme(Long id);
}
