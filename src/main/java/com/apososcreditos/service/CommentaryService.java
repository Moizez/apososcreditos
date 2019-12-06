package com.apososcreditos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apososcreditos.model.Commentary;
import com.apososcreditos.repository.CommentaryRepository;

@Service
public class CommentaryService {
	
	@Autowired
	private CommentaryRepository commentaryRepository;
	
	public Commentary save(Commentary commentary) {
        return commentaryRepository.saveAndFlush(commentary);
    }

	public List<Commentary> findAll(){
		return commentaryRepository.findAll();
	}	
	
	public Commentary findOne(Long id) {
        return commentaryRepository.getOne(id);
    }
     
    public void delete(Long id) {
        commentaryRepository.deleteById(id);
    }
    
    public List<Commentary> findByCommentarsLinkedFilme(Long id){
    	return commentaryRepository.findByCommentarsLinkedFilme(id);
    }
    
}
