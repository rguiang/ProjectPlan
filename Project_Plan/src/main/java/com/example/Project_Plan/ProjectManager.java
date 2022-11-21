package com.example.Project_Plan;

import com.example.Project_Plan.ProjectPlan.Project;
import com.example.Project_Plan.ProjectPlan.Task;
import com.example.Project_Plan.Service.ProjectsService;
import com.example.Project_Plan.Service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ProjectManager {
    private ProjectsService projectsService;
    private TasksService tasksService;
    private TaskManager taskManager;
    public static Scanner input = new Scanner(System.in);
    @Autowired
    public ProjectManager(ProjectsService projectsService,
                          TasksService tasksService,
                          TaskManager taskManager){
        this.projectsService = projectsService;
        this.tasksService = tasksService;
        this.taskManager = taskManager;
    }

    public void projectMenu() {
        System.out.println(intro("Project Menu"));
        System.out.println("Select the number of your choice");
        List<Project> projectList = this.projectsService.getProjectList();
        if(projectList.isEmpty()) {
            System.out.println("1. Create New Project");
            System.out.println("2. Back to Main Menu");
            System.out.print("Enter the number of your choice: ");
            String choice = input.nextLine();
            if(choice.equals("1")) {
                this.projectsService.createProject();
            }else if(choice.equals("2")) {

            }else {
                System.out.println("Invalid Input!!\n");
            }

        } else {
            System.out.println("1. Create New Project");
            System.out.println("2. Select Project");
            System.out.println("3. Edit Project Name");
            System.out.println("4. Delete Project");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter the number of your choice: ");
            String choice = input.nextLine();
            if(choice.equals("1")) {
                this.projectsService.createProject();
            }else if(choice.equals("2")) {
                this.taskManager.taskOption1();
            }else if(choice.equals("3")) {
                this.projectsService.editProjectName();
            }else if(choice.equals("4")) {
                this.projectsService.deleteProject();
            }else {
                System.out.println("Invalid Input!!\n");
            }

        }
    }
    public void displayAllProjects() {
        System.out.println(intro("Project List"));
        this.projectsService.displayProjectList();
    }
    public static String intro(String title) {
        return "\n=================== " + title + " ===================";
    }
}
