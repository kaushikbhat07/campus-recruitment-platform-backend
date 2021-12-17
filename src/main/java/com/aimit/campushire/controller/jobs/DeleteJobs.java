package com.aimit.campushire.controller.jobs;

import com.aimit.campushire.models.Job;
import com.aimit.campushire.repository.JobRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kaushik Bhat
 */

@RestController
@RequestMapping("/job")

public class DeleteJobs {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final JobRepository jobRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public DeleteJobs(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Deletes all jobs
     * @return 200
     */
    @CrossOrigin(host)
    @DeleteMapping("/delete/all")
    public ResponseEntity removeAllJobs() {
        try {
            final List<Job> jobsList = jobRepository.findAll();

            if (jobsList.isEmpty()) {
                logger.info("Jobs list is empty");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            logger.info("Deleting all jobs..");
            jobRepository.deleteAll();

            logger.info("All Jobs deleted. Returning with HTTP 200 & remaining jobs");
            final List<Job> remainingJobs = jobRepository.findAll();

            return new ResponseEntity<>(remainingJobs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Jobs could not be deleted. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes the Job IDs passed in request parameters
     * @param jobIds List of Job IDs
     * @return 200
     */
    @CrossOrigin(host)
    @DeleteMapping("/delete/")
    public ResponseEntity removeJobs(@RequestParam("id") List<Integer> jobIds) {
        try {
            final List<Job> jobsList = jobRepository.findAllById(jobIds);

            if (jobsList.isEmpty()) {
                logger.info("Jobs Not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            final List<Integer> foundJobIds = new ArrayList<>();
            for (Job foundJob : jobsList) {
                foundJobIds.add(foundJob.getJobId());
            }

            logger.info("Deleting the jobs with IDs.." + jobIds);
            jobRepository.deleteAllById(foundJobIds);

            logger.info("All Jobs deleted. Returning with HTTP 200 & remaining jobs");
            final List<Job> remainingJobs = jobRepository.findAll();

            return new ResponseEntity<>(remainingJobs, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Jobs could not be deleted. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}