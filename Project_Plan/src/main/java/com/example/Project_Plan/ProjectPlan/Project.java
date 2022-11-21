package com.example.Project_Plan.ProjectPlan;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Project {
    @Id
    @SequenceGenerator(
            name = "projects_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "projects_sequence"
    )
    private Long id;
    private String projectName;
    private String status;
}
