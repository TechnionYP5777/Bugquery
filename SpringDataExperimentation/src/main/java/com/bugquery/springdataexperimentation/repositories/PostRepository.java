package com.bugquery.springdataexperimentation.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.bugquery.springdataexperimentation.entities.Post;

/**
 * Repository interface for {@link Post}.
 * 
 * @author Amit Ohayon
 * @since May 9, 2017
 */
public interface PostRepository extends CrudRepository<Post, Long> {
	
	List<Post> findByStackTrace(String stackTrace);
	
}
