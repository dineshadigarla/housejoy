package com.staxrt.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.staxrt.tutorial.model.Floor;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {}