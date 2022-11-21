package com.example.Project_Plan.Repository;

import com.example.Project_Plan.ProjectPlan.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task,Long> {

    List<Task> findByProjectId(Long projectId);
    List<Task> deleteAllByProjectId(Long projectId);
    boolean existsByIdAndProjectId(Long taskId,Long projectId);
}
