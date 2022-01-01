package com.aimit.campushire.controller.applicants;

import com.aimit.campushire.models.Applicants;
import com.aimit.campushire.models.Job;
import com.aimit.campushire.models.Student;
import com.aimit.campushire.repository.ApplicantRepository;
import com.aimit.campushire.repository.JobRepository;
import com.aimit.campushire.repository.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Kaushik Bhat
 */

@RestController
@RequestMapping("/applicant")

public class CreateApplicant {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final ApplicantRepository applicantRepository;
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public CreateApplicant(ApplicantRepository applicantRepository, JobRepository jobRepository, StudentRepository studentRepository) {
        this.applicantRepository = applicantRepository;
        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
    }

    /**
     * The method creates a new applicant for a job
     *
     * @param jobId Job identified by Job ID
     * @param studentId Applicant (Student ID)
     * @return 201
     */
    @CrossOrigin(host)
    @PostMapping("/job/{jobId}/student/{studentId}/new")
    public ResponseEntity addApplicant(@PathVariable(name = "jobId") Integer jobId, @PathVariable(name = "studentId") Integer studentId) {
        try {
            logger.info("Adding application..");

            // check if job exists
            Optional<Job> job;
            if (jobRepository.existsById(jobId)) {
                job = jobRepository.findById(jobId);
            } else {
                logger.error("Job with the ID " + jobId + " not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // check if student exists
            Optional<Student> student;
            if (studentRepository.existsById(studentId)) {
                student = studentRepository.findById(studentId);
            } else {
                logger.error("Student with the ID " + studentId + " not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // create applicant object
            Applicants application = new Applicants();
            application.setJob(job.get());
            application.setStudent(student.get());

            Applicants createdApplication = applicantRepository.save(application);
            logger.info("Application saved. ID: " + createdApplication.getApplicationId());

            List<Applicants> applicantsList = Collections.singletonList(createdApplication);
            return new ResponseEntity<>(applicantsList, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Failed to save Application. Exception occurred: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}




















