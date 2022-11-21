package com.example.Project_Plan.ProjectPlan;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class TaskDependencies {
    @Id
    @SequenceGenerator(
            name = "taskDependency_sequence",
            sequenceName = "taskDependency_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "taskDependency_sequence"
    )
    private Long id;
    private Long taskId;
    private Long dependencyId;
}
