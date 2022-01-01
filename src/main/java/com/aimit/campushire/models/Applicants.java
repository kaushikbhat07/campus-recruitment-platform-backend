package com.aimit.campushire.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Data
@Table(name = "applicants", uniqueConstraints = {
    @UniqueConstraint(name = "UKApplications", columnNames = {"studentId", "jobId"})
})
@NoArgsConstructor
public class Applicants {
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
    private int applicationId;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
//    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
//    @JsonIgnore
    private Job job;
}
