package com.aimit.campushire.controller.student;

import com.aimit.campushire.models.Student;
import com.aimit.campushire.repository.StudentRepository;
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
@RequestMapping("/student")

public class DeleteStudents {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final StudentRepository studentRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public DeleteStudents(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Deletes all students
     *
     * @return 200
     */
    @CrossOrigin(host)
    @DeleteMapping("/delete/all")
    public ResponseEntity removeAllStudents() {
        try {
            final List<Student> studentsList = studentRepository.findAll();

            if (studentsList.isEmpty()) {
                logger.info("Students list is empty");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            logger.info("Deleting all students..");
            studentRepository.deleteAll();

            logger.info("All Students deleted. Returning with HTTP 200 & remaining students");
            final List<Student> remainingStudents = studentRepository.findAll();

            return new ResponseEntity<>(remainingStudents, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Students could not be deleted. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes the Student IDs passed in request parameters
     *
     * @param studentIds List of Student IDs
     * @return 200
     */
    @CrossOrigin(host)
    @DeleteMapping("/delete/")
    public ResponseEntity removeStudents(@RequestParam("id") List<Integer> studentIds) {
        try {
            final List<Student> studentsList = studentRepository.findAllById(studentIds);

            if (studentsList.isEmpty()) {
                logger.info("Students Not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            final List<Integer> foundStudentIds = new ArrayList<>();
            for (Student foundStudent : studentsList) {
                foundStudentIds.add(foundStudent.getStudentId());
            }

            logger.info("Deleting the students with IDs.." + studentIds);
            studentRepository.deleteAllById(foundStudentIds);

            logger.info("All Students deleted. Returning with HTTP 200 & remaining students");
            final List<Student> remainingStudents = studentRepository.findAll();

            return new ResponseEntity<>(remainingStudents, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Students could not be deleted. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}