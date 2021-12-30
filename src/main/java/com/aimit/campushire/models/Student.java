package com.aimit.campushire.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * @author Kaushik Bhat
 * Student Entity
 */

@Entity
@Data
@Table(
    name = "student",
    uniqueConstraints = { @UniqueConstraint(name = "UniqueRegNumber", columnNames = { "regNumber" }) }
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentId;

    @NotNull
    @Column(unique=true)
    private long regNumber;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @NotNull
    private Date dob;
    private String address;
    @NotNull
    private long contactNumber;
    @NotNull
    private String semester;
    @NotNull
    private String section;
    @NotNull
    private String branch;
    @NotNull
    private String specialization;
    @NotNull
    private String resume;

    @NotNull
    private Date startYear;
    @NotNull
    private Date endYear;

    @NotNull
    private String gender;
    @NotNull
    private String nationality;
    @NotNull
    private long adharNumber;
    @NotNull
    private String education;
}