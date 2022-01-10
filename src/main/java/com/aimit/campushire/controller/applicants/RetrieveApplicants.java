package com.aimit.campushire.controller.applicants;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Kaushik Bhat
 */

@RestController
@RequestMapping("/applicant")
public class RetrieveApplicants {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final ApplicantRepository applicantRepository;
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public RetrieveApplicants(ApplicantRepository applicantRepository, JobRepository jobRepository, StudentRepository studentRepository) {
        this.applicantRepository = applicantRepository;
        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
    }

    /**
     * Retrieves all applications received for a job
     *
     * @param jobId
     * @return 200
     */
    @GetMapping("/view/job/{jobId}/applications")
    public ResponseEntity getApplicationsByJobId(@PathVariable(name = "jobId") Integer jobId) {
        try {
            final Optional<Job> retrievedJob = jobRepository.findById(jobId);

            if (!retrievedJob.isPresent()) {
                logger.info("Job with IDs: " + jobId + " not found. ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Retrieving applicants for Job ID: " + jobId);

            final Job job = retrievedJob.get();

            return new ResponseEntity<>(applicantRepository.findAllByJob(job), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Jobs could not be retrieved. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all applications made by a student
     *
     * @param studentId
     * @return 200
     */
//    @GetMapping("/view/student/{studentId}/applications")
//    public ResponseEntity getApplicationsByStudentId(@PathVariable(name = "studentId") Integer studentId) {
//        try {
//            final Optional<Student> retrievedStudent = studentRepository.findById(studentId);
//
//            if (!retrievedStudent.isPresent()) {
//                logger.info("Student with IDs: " + studentId + " not found. ");
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            logger.info("Retrieving applications for Student ID: " + studentId);
//
//            final Student student = retrievedStudent.get();
//
//            return new ResponseEntity<>(applicantRepository.findAllByStudent(student), HttpStatus.OK);
//
//        } catch (Exception e) {
//            logger.error("Jobs could not be retrieved. Exception occurred: " + e.getMessage());
//            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
//        }
//    }
}
