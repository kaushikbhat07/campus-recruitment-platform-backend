package com.aimit.campushire.repository;

import com.aimit.campushire.models.Applicants;
import com.aimit.campushire.models.Job;
import com.aimit.campushire.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicantRepository extends JpaRepository<Applicants, Integer> {
    List<Applicants> findAllByJob(Job job);

    List<Applicants> findAllByStudent(Student student);
}