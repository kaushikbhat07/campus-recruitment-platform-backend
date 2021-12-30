package com.aimit.campushire.repository;

import com.aimit.campushire.models.Job;
import com.aimit.campushire.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Student JPA Repository
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

}