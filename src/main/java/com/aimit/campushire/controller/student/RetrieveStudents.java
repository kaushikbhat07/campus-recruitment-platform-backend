package com.aimit.campushire.controller.student;

import com.aimit.campushire.models.Student;
import com.aimit.campushire.repository.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Kaushik Bhat
 */

@RestController
@RequestMapping("/student")

public class RetrieveStudents {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final StudentRepository studentRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public RetrieveStudents(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves all students
     *
     * @return 200
     */
    @GetMapping("/view/all")
    public ResponseEntity getStudents() {
        try {
            final List<Student> students = studentRepository.findAll();

            if (students.isEmpty()) {
                logger.info("Student list is empty! ");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            logger.info("Returning with all students data..");
            return new ResponseEntity<>(students, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Students could not be retrieved. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all students by IDs
     *
     * @param studentIds List of Student IDs
     * @return 200
     */
    @GetMapping("/view/")
    public ResponseEntity getStudentsByIds(@RequestParam("id") List<Integer> studentIds) {
        try {
            final List<Student> studentsList = studentRepository.findAllById(studentIds);

            if (studentsList.isEmpty()) {
                logger.info("Students with IDs: " + studentIds + " not found. ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Retrieving students..");
            for (Student student : studentsList) {
                logger.info("Student ID: " + student.getStudentId());
            }
            return new ResponseEntity<>(studentsList, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Students could not be retrieved. Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}