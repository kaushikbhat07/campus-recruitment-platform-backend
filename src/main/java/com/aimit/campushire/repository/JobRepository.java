package com.aimit.campushire.repository;

import com.aimit.campushire.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Jobs JPA Repository
 */
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query("SELECT j FROM Applicants a INNER JOIN Student s ON a.student.studentId = s.studentId " +
            "INNER JOIN Job j ON j.jobId = a.job.jobId where s.studentId = ?1")
    List<Job> findAppliedJobsByStudentId(Integer studentId);
}