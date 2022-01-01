package com.aimit.campushire.repository;

import com.aimit.campushire.models.Job;
import com.aimit.campushire.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Jobs JPA Repository
 */
public interface JobRepository extends JpaRepository<Job, Integer> {
}