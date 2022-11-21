package com.example.Project_Plan.Service;

import com.example.Project_Plan.ProjectPlan.Project;
import com.example.Project_Plan.Repository.ProjectsRepository;
import com.example.Project_Plan.Repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ProjectsService {
    private final TasksRepository tasksRepository;
    private final ProjectsRepository projectsRepository;
    public static Scanner input = new Scanner(System.in);
    @Autowired
    public ProjectsService(TasksRepository tasksRepository, ProjectsRepository projectsRepository){
        this.tasksRepository = tasksRepository;
        this.projectsRepository = projectsRepository;
    }

    public void createProject() {
        System.out.println("=========== Create Project ===========");
        System.out.print("Enter a project Name: ");
        String projectName = input.nextLine();
        if(projectName.equals("") || projectName == null || projectName.trim().length() == 0){
            System.out.println("Project name must not be null or empty" +
                    "!!");
        } else if (this.projectsRepository.existsProjectByProjectName(projectName)) {
            System.out.println("Sorry the project name you have entered is already exists!!");
        } else {
            Project project = new Project();
            project.setProjectName(projectName);
            project.setStatus("New");
            this.projectsRepository.save(project);
            System.out.println("New Project have been created!!");
        }
    }
    public void displayProjectList() {
        List<Project> projectsList = this.projectsRepository.findAll();
        if(projectsList.isEmpty()) {
            System.out.print("\nThere is no task in this project\n");
        } else {
            String titleTemplate = "%-20s %-20s %-20s%n";
            String template = "%-20s %-20s %-20s%n";
            System.out.printf(titleTemplate,"Project_id", "Project_name", "Status");
            for(Project project : projectsList){
                System.out.printf(template,project.getId(),project.getProjectName(),project.getStatus());
            }
        }
    }
    public void deleteProject() {
        displayProjectList();
        System.out.println("=========== Project Deletion ===========");
        System.out.print("Enter the project id: ");
        String projectid = input.nextLine();
        try {
            Long projectId = Long.parseLong(projectid);
            if(checkProjectIfExists(projectId)) {
                this.projectsRepository.deleteById(projectId);
                this.tasksRepository.deleteAllByProjectId(projectId);
            } else {
                System.out.print("\nProject id is not exists!!\n");
            }
        }catch (Exception e) {
            System.out.print("\nProject id is Invalid!!\n");
        }

    }
    public void editProjectName() {
        displayProjectList();
        System.out.println("=========== Edit Project Name ===========");
        System.out.print("Enter the project id: ");
        String projectid = input.nextLine();
        try {
            Long projectId = Long.parseLong(projectid);
            if(checkProjectIfExists(projectId)) {
                System.out.println("=========== Edit Project Name ===========");
                System.out.print("Enter the project name: ");
                String projectName = input.nextLine();
                if(projectName.equals("") || projectName == null || projectName.trim().length() == 0){
                    System.out.println("Project name must not be null or empty!!");
                } else if (this.projectsRepository.existsProjectByProjectName(projectName)) {
                    System.out.println("Sorry the project name you have entered is already exists!!");
                } else {
                    Project project = getProjectById(projectId).get();
                    project.setProjectName(projectName);
                    this.projectsRepository.save(project);
                    System.out.println("New Project have been created!!");
                }
            } else {
                System.out.print("\nProject id is not exists!!\n");
            }
        }catch (Exception e) {
            System.out.print("\nProject id is Invalid!!\n");
        }
    }
    public List<Project> getProjectList() {
        return this.projectsRepository.findAll();
    }
    public Optional<Project> getProjectById(Long id) {
        return this.projectsRepository.findById(id);
    }
    public boolean checkProjectIfExists(Long projectId) {
        return this.projectsRepository.existsById(projectId);
    }
}
