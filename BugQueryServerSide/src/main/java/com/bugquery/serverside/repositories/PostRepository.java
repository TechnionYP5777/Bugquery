package com.bugquery.serverside.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bugquery.serverside.entities.Post;

/**
 * 
 * @author Tony
 * @since May 11, 2017
 * Repository for storing stack trace searches
 *
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	public List<Post> findByStackTraceException(String exception);
}