package com.example.Project_Plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;
@Service
public class ProjectPlanProcess {
    private TaskManager taskprocess;
    private ProjectManager projectProcess;
    @Autowired
    public ProjectPlanProcess(TaskManager taskprocess,
                              ProjectManager projectProcess){
        this.taskprocess = taskprocess;
        this.projectProcess = projectProcess;
    }
    public static Scanner input = new Scanner(System.in);
    public void process() {
        boolean shutDownProcess = false;
        while (shutDownProcess == false){
            shutDownProcess = mainOption();
        }
    }
    public boolean mainOption() {
        System.out.println(intro("Main Menu"));
        System.out.println("Select the number of your choice");
        System.out.println("1. Project");
        System.out.println("2. Task");
        System.out.println("3. Exit");
        System.out.print("Please enter the number of your choice: ");
        String choice = input.nextLine();

        if(choice.equals("1")) {
            this.projectProcess.displayAllProjects();
            this.projectProcess.projectMenu();
        }else if(choice.equals("2")) {
            this.taskprocess.taskOption1();
        }else if(choice.equals("3")) {
            System.out.println("=========================================================\n\n");
            System.out.println("The App is Closing");
            return true;
        }else {
            System.out.println("Invalid Input!!\n");
        }
        return false;
    }
    public static String intro(String title) {
        return "\n=================== " + title + " ===================";
    }

}

