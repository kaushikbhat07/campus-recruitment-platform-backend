package com.aimit.campushire.controller.jobs;

import com.aimit.campushire.models.Job;
import com.aimit.campushire.repository.JobRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Kaushik Bhat
 */

@RestController
@RequestMapping("/job")

public class UpdateJobs {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final JobRepository jobRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public UpdateJobs(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Updates the job with new info
     * @param newJob The updated job object
     * @return 200
     */
    @CrossOrigin(host)
    @PutMapping("/update")
    public ResponseEntity updateJob(@RequestBody Job newJob) {
        try {
            // check if job exists
            if (!jobRepository.existsById(newJob.getJobId())) {
                logger.info("The job with the ID " + newJob.getJobId() + "was not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Updating job with the ID: " + newJob.getJobId());
            jobRepository.save(newJob);

            final Optional<Job> updatedJob = jobRepository.findById(newJob.getJobId());

            if (updatedJob.isPresent()) {
                logger.info("Updated job with the ID: " + updatedJob.get().getJobId());
                return new ResponseEntity<>(updatedJob.get(), HttpStatus.OK);
            } else {
                logger.info("Job with the ID " + newJob.getJobId() +
                        "was not updated. ");
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            logger.error("Job was not updated successfully. Exception occurred: " + e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}