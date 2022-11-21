package com.example.Project_Plan.Service;

import com.example.Project_Plan.ProjectPlan.Task;
import com.example.Project_Plan.ProjectPlan.TaskDependencies;
import com.example.Project_Plan.Repository.TaskDependenciesRepository;
import com.example.Project_Plan.Repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final TaskDependenciesService taskDependenciesService;
    public static Scanner input = new Scanner(System.in);
    @Autowired
    public TasksService(TasksRepository tasksRepository, TaskDependenciesService taskDependenciesService) {
        this.tasksRepository = tasksRepository;
        this.taskDependenciesService = taskDependenciesService;
    }
    public void displayAllTaskByProjectId(Long projectId){
        List<Task> taskList = this.tasksRepository.findByProjectId(projectId);
        if(taskList.isEmpty()) {
            System.out.print("\nThere is no task in this project\n");
        } else {

            String titleTemplate = "%-20s %-20s %-20s %-20s %-20s %-20s %-20s%n";
            String template = "%-20s %-20s %-20s %-20s %-20s %-20s %-20s%n";
            System.out.printf(titleTemplate,
                    "Task_Id",
                    "Task_Name",
                    "Duration",
                    "Start_Date",
                    "End_Date",
                    "Status",
                    "Dependency");
            for(Task task : taskList){
                String dateStart = dateNullCheker(task.getStartDate());
                String dateEnd = dateNullCheker(task.getEndDate());
                System.out.printf(template,
                        task.getId(),
                        task.getTaskName(),
                        task.getDuration(),
                        dateStart,
                        dateEnd,
                        task.getStatus(),
                        this.taskDependenciesService.checkTaskDependenciesByTaskIdIfExist(task.getId()) ?
                                "Has dependency":"No dependency");
            }
        }
    }
    public void addTask(Long projectId) {
        Task newTask = new Task();
        System.out.print("Enter task name: ");
        String taskName = input.nextLine();
        System.out.print("Enter task description: ");
        String taskDescription = input.nextLine();
        System.out.print("Enter task duration(Number of Hours): ");
        String duration = input.nextLine();
        newTask.setTaskName(taskName);
        newTask.setDescription(taskDescription);
        newTask.setDuration(Float.parseFloat(duration));
        newTask.setProjectId(projectId);
        newTask.setStatus("New");
        this.tasksRepository.save(newTask);

        System.out.println("New Task have been created!!");
    }
    public String dateNullCheker(LocalDateTime date) {
        try {
            if(date != null) {
                return date.toString();
            }else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
    public void displayTask(Long taskId) {
        String template = "%-20s %-20s %-20s %-20s %-20s %-20s %-20s%n";

            Optional<Task> task = this.tasksRepository.findById(taskId);
            String dateStart = dateNullCheker(task.get().getStartDate());
            String dateEnd = dateNullCheker(task.get().getEndDate());
            System.out.printf(template,
                    task.get().getId(),
                    task.get().getTaskName(),
                    task.get().getDuration(),
                    dateStart,
                    dateEnd,
                    task.get().getStatus(),
                    this.taskDependenciesService.checkTaskDependenciesByTaskIdIfExist(task.get().getId()) ?
                            "Has dependency":"No dependency");

    }
    public void editTaskName(Task task, String taskName) {
        task.setTaskName(taskName);
        this.tasksRepository.save(task);
        System.out.println("Task name has been change successfully!!\n");
    }
    public void editTaskDescription(Task task, String taskDescription) {
        task.setDescription(taskDescription);
        this.tasksRepository.save(task);
        System.out.println("Task Description has been change successfully!!\n");
    }
    public void editTaskDuration(Task task, float duration) {
        task.setDuration(duration);
        this.tasksRepository.save(task);
        System.out.println("Duration has been change successfully!!\n");
    }
    public void addTaskDependency(Task task,Long dependencyId) {
        TaskDependencies dependencies = new TaskDependencies();
        dependencies.setTaskId(task.getId());
        dependencies.setDependencyId(dependencyId);
        this.taskDependenciesService.saveDependency(dependencies);
        System.out.println("Dependency has been change successfully!!\n");
    }
    public void saveTask(Task task) {
        this.tasksRepository.save(task);

    }
    public List<Task> getTaskListByProjectId(Long projectId){
        return this.tasksRepository.findByProjectId(projectId);
    }
    public Optional<Task> getTasktById(Long id) {
        return this.tasksRepository.findById(id);
    }
    public boolean checkTaskIfExists(Long taskId) {
        return this.tasksRepository.existsById(taskId);
    }
    public boolean checkTaskIfExists(Long taskId,Long projectId) {
        return this.tasksRepository.existsByIdAndProjectId(taskId, projectId);
    }
    public void deleteTaskById(Long taskId) {
        this.tasksRepository.deleteById(taskId);
    }
}
