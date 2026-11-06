package com.example.myspringbootapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myspringbootapp.entity.Visit;

public interface VisitDao extends JpaRepository<Visit, Long> {
    List<Visit> findTop5ByOrderByVisitedAtDesc();

}