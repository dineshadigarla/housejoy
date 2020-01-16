package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Building;

@Repository
public interface BuildingsRepository extends JpaRepository<Building, Long> {}
