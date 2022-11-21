package com.example.Project_Plan.Service;

import com.example.Project_Plan.ProjectPlan.TaskDependencies;
import com.example.Project_Plan.Repository.TaskDependenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDependenciesService {
    private final TaskDependenciesRepository taskDependenciesRepository;
    @Autowired
    public TaskDependenciesService(TaskDependenciesRepository taskDependenciesRepository) {
        this.taskDependenciesRepository = taskDependenciesRepository;
    }
    public boolean checkTaskDependenciesByTaskIdIfExist(Long taskId) {
        return taskDependenciesRepository.existsTaskDependenciesRepositoryByTaskId(taskId);
    }
    public boolean checkTaskDependentByTaskIdIfExist(Long taskId) {
        return taskDependenciesRepository.existsTaskDependenciesRepositoryByDependencyId(taskId);
    }
    public boolean checkTaskDependentByTaskIdAndDependencyIdIsExist(Long taskId, Long dependencyId) {
        return taskDependenciesRepository.existsTaskDependenciesRepositoryByTaskIdAndDependencyId(taskId,dependencyId);
    }
    public List<TaskDependencies> getDependentTask(Long dependentId) {
        return this.taskDependenciesRepository.findAllByDependencyId(dependentId);
    }
    public List<TaskDependencies> getDependenciesTaskByTaskId(Long taskId) {
        return this.taskDependenciesRepository.findAllByTaskId(taskId);
    }
    public void saveDependency(TaskDependencies taskDependencies){
        this.taskDependenciesRepository.save(taskDependencies);
    }
    public void deleteDependency(List<TaskDependencies> dependencies){
        this.taskDependenciesRepository.deleteAllInBatch(dependencies);
    }
    public List<TaskDependencies> findByTaskIdAndDependencyId(Long taskId, Long dependencyId) {
        return this.taskDependenciesRepository.findByTaskIdAndDependencyId(taskId, dependencyId);
    }
}
