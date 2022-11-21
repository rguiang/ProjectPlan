package com.example.Project_Plan;

import com.example.Project_Plan.ProjectPlanProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectPlanApplication {

	private ProjectPlanProcess planProcess;
	@Autowired
	public ProjectPlanApplication(ProjectPlanProcess planProcess){
		this.planProcess = planProcess;
		ApplicationProcess();
	}
	public static void main(String[] args) {
		SpringApplication.run(ProjectPlanApplication.class, args);
	}
	public void ApplicationProcess() {
		this.planProcess.process();
	}
}
