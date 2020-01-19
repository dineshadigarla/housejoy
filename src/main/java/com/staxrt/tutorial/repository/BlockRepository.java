package com.staxrt.tutorial.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.staxrt.tutorial.model.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
	
	
}

