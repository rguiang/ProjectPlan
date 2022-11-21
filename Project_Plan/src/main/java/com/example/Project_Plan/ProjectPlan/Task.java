package com.example.Project_Plan.ProjectPlan;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private Long projectId;
    private String taskName;
    private String description;
    private float duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
