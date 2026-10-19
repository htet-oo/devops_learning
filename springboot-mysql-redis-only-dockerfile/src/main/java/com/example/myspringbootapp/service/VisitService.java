
package com.example.myspringbootapp.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.myspringbootapp.dao.VisitDao;
import com.example.myspringbootapp.entity.Visit;

@Service
public class VisitService {

    private final VisitDao visitDao;

    public VisitService(VisitDao visitDao) {
        this.visitDao = visitDao;
    }

    @Cacheable("visits")
    public List<Visit> getLatestVisits() {
        System.out.println("Fetching visits from MySQL...");
        return visitDao.findTop5ByOrderByVisitedAtDesc();
    }
    
    @CacheEvict(value = "visits", allEntries = true)
    public void createVisit() {
        System.out.println(">>> createVisit() called");

        Visit visit = new Visit(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
        visitDao.save(visit);
    }
}
