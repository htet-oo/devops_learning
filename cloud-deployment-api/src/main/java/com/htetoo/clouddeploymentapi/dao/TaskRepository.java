package com.htetoo.clouddeploymentapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htetoo.clouddeploymentapi.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}