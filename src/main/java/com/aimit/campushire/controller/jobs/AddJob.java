package com.aimit.campushire.controller.jobs;

import com.aimit.campushire.models.Job;
import com.aimit.campushire.repository.JobRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Kaushik Bhat
 */

@RestController
@RequestMapping("/job")

public class AddJob {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final JobRepository jobRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public AddJob(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * The method is create a single new job opening
     *
     * @param newJobOpening new job object received from request body
     * @return 201
     */
    @CrossOrigin(host)
    @PostMapping("/new")
    public ResponseEntity saveJob(@RequestBody Job newJobOpening) {
        try {
            logger.info(newJobOpening);

            logger.info("Adding new job opening.." + newJobOpening);
            final Job savedJob = jobRepository.save(newJobOpening);
            logger.info("New job added with the ID: " + savedJob.getJobId());
            return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            logger.info("Failed to save Job. ResponseStatusException: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            logger.error("Failed to save Job. Exception occurred: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * The method is create multiple new job openings
     * @param newJobOpenings List of job objects - multiple job openings
     * @return 201
     */
    @CrossOrigin(host)
    @PostMapping("/new/multiple")
    public ResponseEntity saveMultipleJob(@RequestBody List<Job> newJobOpenings) {
        try {
            logger.info(newJobOpenings);

            logger.info("Adding multiple new job openings.." + newJobOpenings);
            final List<Job> savedJobs = jobRepository.saveAll(newJobOpenings);

            for (Job savedJob: savedJobs) {
                logger.info("New job added with the ID: " + savedJob.getJobId());
            }

            return new ResponseEntity<>(savedJobs, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            logger.info("Failed to save Jobs. ResponseStatusException: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            logger.error("Failed to save Jobs. Exception occurred: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}