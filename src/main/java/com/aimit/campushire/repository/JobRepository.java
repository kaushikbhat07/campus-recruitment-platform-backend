package com.aimit.campushire.repository;

import com.aimit.campushire.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {

}