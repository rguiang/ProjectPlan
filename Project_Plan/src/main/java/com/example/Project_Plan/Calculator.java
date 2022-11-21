package com.example.Project_Plan;

import com.example.Project_Plan.ProjectPlan.Task;
import com.example.Project_Plan.Repository.TaskDependenciesRepository;
import com.example.Project_Plan.Repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class Calculator {
    private final TasksRepository tasksRepository;
    private final TaskDependenciesRepository taskDependenciesRepository;
    @Autowired
    public Calculator(TasksRepository tasksRepository, TaskDependenciesRepository taskDependenciesRepository) {
        this.tasksRepository = tasksRepository;
        this.taskDependenciesRepository = taskDependenciesRepository;
    }
    public LocalDateTime calculateEndDateOnNoDependencies(Task task, LocalDateTime startDateTime) {
        float duration = task.getDuration() * 60;
        return startDateTime.plusMinutes((long) duration);
    }
}
