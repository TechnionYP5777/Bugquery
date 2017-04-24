package com.bugquery.serverside.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bugquery.serverside.entities.StackOverflowPost;

/**
 * 
 * @author Tony
 * @since Apr 23, 2017
 * Repository for storing stack trace searches
 *
 */
@Repository
public interface StackOverflowPostRepository extends CrudRepository<StackOverflowPost, Long> {
  // Empty, Spring boot automatically extends this.
}