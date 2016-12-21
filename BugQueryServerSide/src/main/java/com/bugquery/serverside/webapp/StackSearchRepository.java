package com.bugquery.serverside.webapp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StackSearchRepository extends CrudRepository<StackSearch, Long> {
}