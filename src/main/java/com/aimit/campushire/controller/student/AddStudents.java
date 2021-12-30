package com.aimit.campushire.controller.student;

import com.aimit.campushire.models.Student;
import com.aimit.campushire.repository.StudentRepository;
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
@RequestMapping("/student")

public class AddStudents {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final StudentRepository studentRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public AddStudents(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * The method is create a single new student
     *
     * @param newStudent new student object received from request body
     * @return 201
     */
    @CrossOrigin(host)
    @PostMapping("/new")
    public ResponseEntity saveStudent(@RequestBody Student newStudent) {
        try {
            logger.info(newStudent);

            logger.info("Adding new student.." + newStudent);
            final Student savedStudent = studentRepository.save(newStudent);
            logger.info("New student added with the ID: " + savedStudent.getStudentId());
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            logger.info("Failed to save Student. ResponseStatusException: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            logger.error("Failed to save Student. Exception occurred: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * The method creates multiple new students
     *
     * @param newStudent List of student objects - multiple students
     * @return 201
     */
    @CrossOrigin(host)
    @PostMapping("/new/multiple")
    public ResponseEntity saveMultipleStudent(@RequestBody List<Student> newStudent) {
        try {
            logger.info(newStudent);

            logger.info("Adding multiple new student openings.." + newStudent);
            final List<Student> savedStudents = studentRepository.saveAll(newStudent);

            for (Student savedStudent : savedStudents) {
                logger.info("New student added with the ID: " + savedStudent.getStudentId());
            }

            return new ResponseEntity<>(savedStudents, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            logger.info("Failed to save Students. ResponseStatusException: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            logger.error("Failed to save Students. Exception occurred: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}