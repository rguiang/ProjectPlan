package com.example.Project_Plan.Repository;

import com.example.Project_Plan.ProjectPlan.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Project,Long> {
    boolean existsProjectByProjectName(String projectName);
}
