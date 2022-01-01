package com.aimit.campushire.repository;

import com.aimit.campushire.models.Applicants;
import com.aimit.campushire.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicants, Integer> {
    Optional<Applicants> findStudentByApplicationId(Integer applicationId);
}