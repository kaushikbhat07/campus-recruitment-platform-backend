package com.aimit.campushire.repository;

import com.aimit.campushire.models.Job;
import com.aimit.campushire.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Student JPA Repository
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Job j, Student s INNER JOIN Applicants a ON s.studentId = a.student.studentId WHERE j.jobId = ?1")
    List<Student> findApplicantsByJobId(Integer jobId);
}