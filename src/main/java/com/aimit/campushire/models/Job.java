package com.aimit.campushire.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * @author Kaushik Bhat
 * Jobs Entity
 */
@Entity
@Data
@Table(name = "jobs")
@NoArgsConstructor
public class Job {
    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @LastModifiedDate
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
    List<Applicants> applicants;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int jobId;
    @NotNull
    private String company;
    @NotNull
    private String ctc;
    private String eligibility;
    @NotNull
    private String jobDesc;
    private String offerType;

//    @ManyToMany(mappedBy = "appliedJobs", fetch = FetchType.LAZY)
//    @JsonIgnore
//    List<Student> appliedStudents;
    private String misc;
}