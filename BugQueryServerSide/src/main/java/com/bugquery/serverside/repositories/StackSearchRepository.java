package com.bugquery.serverside.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bugquery.serverside.entities.StackSearch;

@Repository
public interface StackSearchRepository extends CrudRepository<StackSearch, Long> {
  // Empty, Spring boot automaticly extends this.
}