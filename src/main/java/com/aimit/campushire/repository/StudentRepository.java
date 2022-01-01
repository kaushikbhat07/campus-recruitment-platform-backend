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
    @Query("SELECT s FROM Applicants a INNER JOIN Student s ON a.student.studentId = s.studentId INNER JOIN Job j " +
            "ON j.jobId = a.job.jobId where j.jobId = ?1")
    List<Student> findApplicantsByJobId(Integer jobId);
}