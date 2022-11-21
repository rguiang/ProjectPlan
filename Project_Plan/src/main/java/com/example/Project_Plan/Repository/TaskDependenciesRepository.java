package com.example.Project_Plan.Repository;

import com.example.Project_Plan.ProjectPlan.Task;
import com.example.Project_Plan.ProjectPlan.TaskDependencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDependenciesRepository extends JpaRepository<TaskDependencies,Long> {
    boolean existsTaskDependenciesRepositoryByTaskId(Long taskId);
    boolean existsTaskDependenciesRepositoryByTaskIdAndDependencyId(Long taskId, Long dependencyId);
    boolean existsTaskDependenciesRepositoryByDependencyId(Long taskId);
    List<TaskDependencies> findAllByDependencyId(Long dependencyId);
    List<TaskDependencies> findAllByTaskId(Long taskId);

    List<TaskDependencies> findByTaskIdAndDependencyId(Long taskId, Long dependencyId);
}
