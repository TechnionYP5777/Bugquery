package com.bugquery.serverside.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bugquery.serverside.entities.StackSearch;

/**
 * 
 * @author Amit
 * @since Dec 24, 2016
 * Repository for storing stack trace searches
 *
 */
@Repository
public interface StackSearchRepository extends CrudRepository<StackSearch, Long> {
  // Empty, Spring boot automatically extends this.
}