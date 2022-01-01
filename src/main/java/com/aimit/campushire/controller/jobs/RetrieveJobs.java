package com.aimit.campushire.controller.jobs;

import com.aimit.campushire.models.Applicants;
import com.aimit.campushire.models.Job;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

/**
 * @author Kaushik Bhat
 */

@RestController
@RequestMapping("/job")

public class RetrieveJobs {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final JobRepository jobRepository;
    private final ApplicantRepository applicantRepository;

    private final StudentRepository studentRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public RetrieveJobs(JobRepository jobRepository, ApplicantRepository applicantRepository, StudentRepository studentRepository) {
        this.jobRepository = jobRepository;
        this.applicantRepository = applicantRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves all jobs
     * @return 200
     */
    @GetMapping("/view/all")
    public ResponseEntity getJobs() {
        try {
            final List<Job> jobs = jobRepository.findAll();

            if (jobs.isEmpty()) {
                logger.info("Job list is empty! ");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            logger.info("Returning with jobs data..");
            return new ResponseEntity<>(jobs, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Jobs could not be retrieved. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all jobs by IDs
     * @param jobIds List of Job IDs
     * @return
     */
    @GetMapping("/view/")
    public ResponseEntity getJobsByIds(@RequestParam("id") List<Integer> jobIds) {
        try {
            final List<Job> jobsList = jobRepository.findAllById(jobIds);

            if (jobsList.isEmpty()) {
                logger.info("Jobs with IDs: " + jobIds + " not found. ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Retrieving jobs..");
            for (Job job : jobsList) {
                logger.info("Job ID: " + job.getJobId());
            }
            return new ResponseEntity<>(jobsList, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Jobs could not be retrieved. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * View all applicants (students) for a provied job
     * @param jobId Job ID of the job
     * @return 200
     */
    @GetMapping("/view/{id}/applicants")
    public ResponseEntity getApplicantsByJobId(@PathVariable(name = "id") Integer jobId) {
        try {
            final Optional<Job> retrievedJob = jobRepository.findById(jobId);

            if (!retrievedJob.isPresent()) {
                logger.info("Job with IDs: " + jobId + " not found. ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Retrieving applicants for Job ID: " + jobId);

            final Job job = retrievedJob.get();

            return new ResponseEntity<>(studentRepository.findApplicantsByJobId(job.getJobId()), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Jobs could not be retrieved. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}