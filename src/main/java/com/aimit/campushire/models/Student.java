package com.aimit.campushire.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;

/**
 * @author Kaushik Bhat
 * Student Entity
 */

@Entity
@Data
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueRegNumber", columnNames = {"regNumber"}),
                @UniqueConstraint(name = "UniqueAdharNumber", columnNames = {"adharNumber"}),
                @UniqueConstraint(name = "UniqueContactNumber", columnNames = {"contactNumber"})
        }
)
@NoArgsConstructor
public class Student {
    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @LastModifiedDate
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    List<Applicants> applications;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentId;
    @NotNull
    @Column(unique = true)
    private long regNumber;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private java.sql.Date dob;
    private String address;
    @Column(nullable = false)
    private long contactNumber;
    @Column(nullable = false)
    private String semester;
    @Column(nullable = false)
    private String section;
    @Column(nullable = false)
    private String branch;
    private String specialization;
    @Column(nullable = false)
    private String resume;
    @Column(nullable = false)
    private java.sql.Date startYear;
    @Column(nullable = false)
    private java.sql.Date endYear;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private long adharNumber;

//    @JoinTable(
//            name = "applications",
//            joinColumns = @JoinColumn(name = "studentId"),
//            inverseJoinColumns = @JoinColumn(name = "jobId"))
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JsonIgnore
//    List<Job> appliedJobs;
    @Column(nullable = false)
    private String education;
}