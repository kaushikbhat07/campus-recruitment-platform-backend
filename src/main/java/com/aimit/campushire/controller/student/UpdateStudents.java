package com.aimit.campushire.controller.student;

import com.aimit.campushire.models.Student;
import com.aimit.campushire.repository.StudentRepository;
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
@RequestMapping("/student")

public class UpdateStudents {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final StudentRepository studentRepository;
    private final String host = "${app.host:default}";

    @Autowired
    public UpdateStudents(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Updates the student with new info
     *
     * @param newStudent The updated student object
     * @return 200
     */
    @CrossOrigin(host)
    @PutMapping("/update")
    public ResponseEntity updateStudent(@RequestBody Student newStudent) {
        try {
            // check if student exists
            if (!studentRepository.existsById(newStudent.getStudentId())) {
                logger.info("The student with the ID " + newStudent.getStudentId() + "was not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Updating student with the ID: " + newStudent.getStudentId());
            studentRepository.save(newStudent);

            final Optional<Student> updatedStudent = studentRepository.findById(newStudent.getStudentId());

            if (updatedStudent.isPresent()) {
                logger.info("Updated student with the ID: " + updatedStudent.get().getStudentId());
                return new ResponseEntity<>(updatedStudent.get(), HttpStatus.OK);
            } else {
                logger.info("Student with the ID " + newStudent.getStudentId() +
                        "was not updated. ");
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            logger.error("Student was not updated successfully. Exception occurred: " + e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}