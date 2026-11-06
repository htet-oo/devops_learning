
package com.example.myspringbootapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myspringbootapp.entity.Visit;
import com.example.myspringbootapp.service.VisitService;

@RestController
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/visits")
    public List<Visit> getLatestVisits() {
        return visitService.getLatestVisits();
    }

    @PostMapping("/visits")
    public String createVisit() {
        visitService.createVisit();
        return "Visit created successfully!";
    }
}
