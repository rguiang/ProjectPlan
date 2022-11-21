package com.example.Project_Plan;

import com.example.Project_Plan.ProjectPlan.Project;
import com.example.Project_Plan.ProjectPlan.Task;
import com.example.Project_Plan.ProjectPlan.TaskDependencies;
import com.example.Project_Plan.Service.ProjectsService;
import com.example.Project_Plan.Service.TaskDependenciesService;
import com.example.Project_Plan.Service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class TaskManager {
    private ProjectsService projectsService;
    private TasksService tasksService;
    private final TaskDependenciesService taskDependenciesService;
    private final Calculator calculator;
    public static Scanner input = new Scanner(System.in);
    @Autowired
    public TaskManager(ProjectsService projectsService,
                       TasksService tasksService,
                       TaskDependenciesService taskDependenciesService, Calculator calculator){
        this.projectsService = projectsService;
        this.tasksService = tasksService;
        this.taskDependenciesService = taskDependenciesService;
        this.calculator = calculator;
    }
    public void taskOption1() {
        System.out.println(intro("Task"));
        this.projectsService.displayProjectList();
        System.out.print("Enter the project id: ");
        String projectId = input.nextLine();
        try {
            Long projectid = Long.parseLong(projectId);
            if (this.projectsService.checkProjectIfExists(projectid)) {
                Optional<Project> project = this.projectsService.getProjectById(projectid);
                taskOption2(project.get());
            } else {
                System.out.print("\nProject id is not exists!!\n");
            }
        } catch (Exception e) {
            System.out.print("\nProject id is Invalid!!\n");
        }
    }
    public void taskOption2(Project project) {
        System.out.println(intro("Project Name: " + project.getProjectName()));
        this.tasksService.displayAllTaskByProjectId(project.getId());
        System.out.println("Select the number of your choice");
        List<Task> taskList = this.tasksService.getTaskListByProjectId(project.getId());
        if(taskList.isEmpty()) {
            System.out.println("1. Add Task");
            System.out.println("2. Back to Main Menu");
            System.out.print("Enter the number of your choice: ");
            String choice = input.nextLine();
            if(choice.equals("1")) {
                this.tasksService.addTask(project.getId());
            }else if(choice.equals("2")) {

            }else {
                System.out.println("Invalid Input!!\n");
            }
        } else {
            System.out.println("1. Add Task");
            System.out.println("2. Select Task");
            System.out.println("3. Delete Task");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter the number of your choice: ");
            String choice = input.nextLine();
            if(choice.equals("1")) {
                this.tasksService.addTask(project.getId());
            }else if(choice.equals("2")) {
                System.out.print("Enter the task id: ");
                String taskId = input.nextLine();
                try {
                    Long taskid = Long.parseLong(taskId);
                    if (this.tasksService.checkTaskIfExists(taskid,project.getId())) {
                        taskOption3(taskid);
                    } else {
                        System.out.print("\nTask id is not exists!!\n");
                    }
                } catch (Exception e) {
                    System.out.print("\nTask id is Invalid!!\n");
                }

            }else if(choice.equals("3")) {
                deleteProjectTask(project);
            }else if(choice.equals("4")) {

            }else {
                System.out.println("Invalid Input!!\n");
            }

        }
    }
    public void taskOption3(Long taskId) {
        Optional<Task> task = this.tasksService.getTasktById(taskId);
        System.out.println(intro("Task Name: " + task.get().getTaskName()));
        String titleTemplate = "%-20s %-20s %-20s %-20s %-20s %-20s %-20s%n";
        System.out.printf(titleTemplate,
                "Task_Id",
                "Task_Name",
                "Duration",
                "Start_Date",
                "End_Date",
                "Status",
                "Dependency");
        this.tasksService.displayTask(taskId);
        System.out.println("\nTask Descriptions: " + task.get().getDescription());

        System.out.println(intro("Menu"));
        System.out.println("Select the number of your choice");
        if(this.taskDependenciesService.checkTaskDependenciesByTaskIdIfExist(taskId)) {
            System.out.println("1. Edit Task");
            System.out.println("2. View Task Dependencies");
            System.out.println("3. Add Task Dependencies");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter the number of your choice: ");
            String choice = input.nextLine();
            if(choice.equals("1")) {
                editTaskOption(task.get());
            }else if(choice.equals("2")) {
                System.out.println("\nDependencies List for " + task.get().getTaskName());
                displayAllDependencies(task.get());
                dependencyOption(task.get());
            }else if(choice.equals("3")) {
                addTaskDependency(task.get());
            }else if(choice.equals("4")) {

            }else {
                System.out.println("Invalid Input!!\n");
            }
        }else {
            System.out.println("1. Edit Task");
            System.out.println("2. Set Start Date");
            System.out.println("3. Add Task Dependencies");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter the number of your choice: ");
            String choice = input.nextLine();
            if(choice.equals("1")) {
                editTaskOption(task.get());
            }else if(choice.equals("2")) {
                setStartDateOnNoDependencies(task.get());
            }else if(choice.equals("3")) {
                addTaskDependency(task.get());
            }else if(choice.equals("4")) {

            }else {
                System.out.println("Invalid Input!!\n");
            }
        }
    }
    public void editTaskOption(Task task) {
        System.out.println(intro("Menu"));
        String titleTemplate = "%-20s %-20s %-20s %-20s %-20s %-20s %-20s%n";
        System.out.printf(titleTemplate,
                "Task_Id",
                "Task_Name",
                "Duration",
                "Start_Date",
                "End_Date",
                "Status",
                "Dependency");
        this.tasksService.displayTask(task.getId());
        System.out.println("Select the number of your choice");
        System.out.println("1. Edit Name");
        System.out.println("2. Edit Duration");
        System.out.println("3. Edit Description");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter the number of your choice: ");
        String choice = input.nextLine();
        if(choice.equals("1")) {
            System.out.print("Enter new name: ");
            String taskName = input.nextLine();
            if(taskName.trim().length() == 0) {
                System.out.println("Task name must not be null or empty!!");
            } else {
                this.tasksService.editTaskName(task, taskName);
                taskOption3(task.getId());
            }
        }else if(choice.equals("2")) {
            System.out.print("Enter number of hours: ");
            String duration = input.nextLine();
            try {
                float numberOfHours = Float.parseFloat(duration);
                this.tasksService.editTaskDuration(task, numberOfHours);
                taskOption3(task.getId());
            } catch (Exception e) {
                System.out.println("Duration must be whole numbers!!\n");
            }
        }else if(choice.equals("3")) {
            System.out.print("Enter new description: ");
            String taskDescription = input.nextLine();
            if(taskDescription.trim().length() == 0) {
                System.out.println("Task name must not be null or empty!!");
            } else {
                this.tasksService.editTaskDescription(task, taskDescription);
                taskOption3(task.getId());
            }
        }else if(choice.equals("4")) {

        }else {
            System.out.println("Invalid Input!!\n");
        }

    }
    public void setStartDateOnNoDependencies(Task task) {
        if(this.taskDependenciesService.checkTaskDependenciesByTaskIdIfExist(task.getId())) {
            System.out.println("Start date cannot be set on Task with dependencies");
        }else {
            System.out.print("Enter the start date(YYYY/MM/DD HH:MM): ");
            String startDateString = input.nextLine();
            try {
                String[] startDateSplit = startDateString.split(" ");
                String[] DateSplit = startDateSplit[0].split("/");
                String[] timeSplit = startDateSplit[1].split(":");

                LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(DateSplit[0]),
                        Integer.parseInt(DateSplit[1]),
                        Integer.parseInt(DateSplit[2]),Integer.parseInt(timeSplit[0]),Integer.parseInt(timeSplit[1]));
                updateTaskDate(task,startDate);
                System.out.println("Date has been set Successfully!!\n");
            }catch (Exception e) {
                System.out.println("Invalid DateTime!!"+e.getMessage());
            }
        }
    }
    public void addTaskDependency(Task task) {
        System.out.println(intro("Add Task Dependency for " + task.getTaskName()));
        this.projectsService.displayProjectList();
        System.out.print("Enter the project id: ");
        String projectId = input.nextLine();
        try {
            Long projectid = Long.parseLong(projectId);
            if (this.projectsService.checkProjectIfExists(projectid)) {
                System.out.println(intro("Add Task Dependency for " + task.getTaskName()));
                this.tasksService.displayAllTaskByProjectId(projectid);
                System.out.print("Enter the task id: ");
                String taskDependencyId = input.nextLine();
                try {
                    Long dependencyId = Long.parseLong(taskDependencyId);
                    if(this.tasksService.checkTaskIfExists(dependencyId)) {
                        if (task.getId() == dependencyId) {
                            System.out.print("\nYou cant make task dependency on itself!!\n");
                        } else if (this.taskDependenciesService.checkTaskDependentByTaskIdAndDependencyIdIsExist(task.getId(),dependencyId)) {
                            System.out.print("\nThis task is already dependency of the selected task!!\n");
                        } else {
                            this.tasksService.addTaskDependency(task, dependencyId);

                            List<TaskDependencies> dependencies = this.taskDependenciesService.getDependenciesTaskByTaskId(task.getId());

                            boolean dependencyNullChecker = false;
                            Task highestEndDateTask = new Task();
                            for(TaskDependencies dependency : dependencies) {
                                Optional<Task> dependencyTask = this.tasksService.getTasktById(dependency.getDependencyId());
                                if(dependencyTask.get().getEndDate() == null) {
                                    dependencyNullChecker = true;
                                } else if (highestEndDateTask.getEndDate() == null) {
                                    highestEndDateTask = dependencyTask.get();
                                } else {
                                    if(dependencyTask.get().getEndDate().isAfter(highestEndDateTask.getEndDate())) {
                                        highestEndDateTask = dependencyTask.get();
                                    }
                                }
                            }
                            if(!dependencyNullChecker){
                                updateTaskDate(task,highestEndDateTask.getEndDate());
                            } else{
                                removeStartEndDateOfAllDependent(task);
                            }
                        }
                    } else {
                        System.out.print("\nTask id is not exists!!\n");
                    }
                } catch (Exception e) {
                    System.out.print("\nTask id is Invalid!!\n" + e.getCause());
                }
            } else {
                System.out.print("\nProject id is not exists!!\n");
            }
        } catch (Exception e) {
            System.out.print("\nProject id is Invalid!!\n");
        }

    }
    public void removeStartEndDateOfAllDependent(Task task) {
        task.setStartDate(null);
        task.setStatus("new");
        task.setEndDate(null);
        this.tasksService.saveTask(task);

        if(this.taskDependenciesService.checkTaskDependentByTaskIdIfExist(task.getId())) {
            List<TaskDependencies> dependents = this.taskDependenciesService.getDependentTask(task.getId());
            for(TaskDependencies dependent : dependents) {
                Optional<Task> dependentTask = this.tasksService.getTasktById(dependent.getTaskId());
                removeStartEndDateOfAllDependent(dependentTask.get());
            }
        }

    }
    public void updateTaskDate(Task task, LocalDateTime dateTime) {
        task.setStartDate(dateTime);
        task.setStatus("Assign");
        task.setEndDate(this.calculator.calculateEndDateOnNoDependencies(task, dateTime));
        this.tasksService.saveTask(task);

        if(this.taskDependenciesService.checkTaskDependentByTaskIdIfExist(task.getId())) {
            List<TaskDependencies> dependents = this.taskDependenciesService.getDependentTask(task.getId());
            for(TaskDependencies dependent : dependents) {
                Optional<Task> dependentTask = this.tasksService.getTasktById(dependent.getTaskId());
                List<TaskDependencies> dependencies = this.taskDependenciesService.getDependenciesTaskByTaskId(dependentTask.get().getId());

                boolean dependencyNullChecker = false;
                Task highestEndDateTask = task;
                for(TaskDependencies dependency : dependencies) {
                    Optional<Task> dependencyTask = this.tasksService.getTasktById(dependency.getDependencyId());
                    if(dependencyTask.get().getEndDate() == null) {
                        dependencyNullChecker = true;
                    } else {
                        if(dependencyTask.get().getEndDate().isAfter(highestEndDateTask.getEndDate())) {
                            highestEndDateTask = dependencyTask.get();
                        }
                    }
                }
                if(!dependencyNullChecker){
                    updateTaskDate(dependentTask.get(),highestEndDateTask.getEndDate());
                }
            }
        }
    }
    public void dependencyOption(Task task) {
        System.out.println("Select the number of your choice");
        System.out.println("1. Add Task Dependencies");
        System.out.println("2. Delete Task Dependencies");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter the number of your choice: ");
        String choice = input.nextLine();
        if(choice.equals("1")) {
            addTaskDependency(task);
        }else if(choice.equals("2")) {
            deleteDependency(task);
        }else if(choice.equals("3")) {
        }
    }
    public void deleteDependency(Task task){
        displayAllDependencies(task);
        System.out.print("Enter task id of task dependency that you want to delete: ");
        String taskId = input.nextLine();
        try {
            Long taskid = Long.parseLong(taskId);
            if(this.tasksService.checkTaskIfExists(taskid)) {
                if (this.taskDependenciesService.checkTaskDependentByTaskIdAndDependencyIdIsExist(task.getId(), taskid)) {
                    this.taskDependenciesService.deleteDependency(this.taskDependenciesService.findByTaskIdAndDependencyId(task.getId(), taskid));
                    System.out.print("\nDependency has been deleted!!\n");
                } else {
                    System.out.print("\nThis task is not A dependency of the selected task!!\n");
                }
            } else {
                System.out.print("\nTask id is not exists!!\n");
            }
        } catch (Exception e) {
            System.out.print("\nTask id is Invalid!!\n");
        }
    }
    public void displayAllDependencies(Task task) {
        System.out.println(intro("Task Dependencies list for " + task.getTaskName()));
        List<TaskDependencies> taskDependencies = this.taskDependenciesService.getDependenciesTaskByTaskId(task.getId());
        String titleTemplate = "%-20s %-20s %-20s %-20s %-20s %-20s %-20s%n";
        System.out.printf(titleTemplate,
                "Task_Id",
                "Task_Name",
                "Duration",
                "Start_Date",
                "End_Date",
                "Status",
                "Dependency");
        for(TaskDependencies taskDependency : taskDependencies) {
            this.tasksService.displayTask(taskDependency.getDependencyId());
        }
    }
    public void deleteProjectTask(Project project) {
        System.out.print("Enter task id of task that you want to delete: ");
        String taskId = input.nextLine();
        try {
            Long taskid = Long.parseLong(taskId);
            List<TaskDependencies> taskDependencies = this.taskDependenciesService.getDependenciesTaskByTaskId(taskid);
            List<TaskDependencies> dependents = this.taskDependenciesService.getDependentTask(taskid);
            this.tasksService.deleteTaskById(taskid);
            this.taskDependenciesService.deleteDependency(taskDependencies);
            this.taskDependenciesService.deleteDependency(dependents);
            System.out.print("\nTask has been deleted!!\n");
        } catch (Exception e) {
            System.out.print("\nTask id is Invalid!!\n");
        }
    }
    public static String intro(String title) {
        return "\n=================== " + title + " ===================";
    }
}
