package com.example.myspringbootapp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Visit implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitedAt;

    public Visit() {
    }

    public Visit(LocalDateTime visitedAt) {
        this.visitedAt = visitedAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getVisitedAt() {
        return visitedAt;
    }
}
