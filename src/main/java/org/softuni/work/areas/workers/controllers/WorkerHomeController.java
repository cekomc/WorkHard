package org.softuni.work.areas.workers.controllers;

import org.softuni.work.areas.jobs.entities.Job;
import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class WorkerHomeController {

    private final WorkerService workerService;
    private final ProjectService projectService;
    private final JobService jobService;

    @Autowired
    public WorkerHomeController(WorkerService workerService, ProjectService projectService, JobService jobService) {
        this.workerService = workerService;
        this.projectService = projectService;
        this.jobService = jobService;
    }


    @GetMapping("/career-home")
    private ModelAndView careerHome(ModelAndView modelAndView) {
        modelAndView.setViewName("career-home");
        // org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // String email = auth.getName();
        // Worker worker = this.workerService.loadWorkerByEmail(email);
        // if(worker.getRoles().toString().equals("ROLE_ADMIN")){
        //     modelAndView.setViewName("admin-home");
        // }else if(worker.getRoles().toString().equals("ROLE_PROJECT_MANAGER")){
        //     modelAndView.setViewName("project-manager-home");
        // }else {
        //     if(worker.getJob()==null){
        //         modelAndView.setViewName("career-home-for-noobs");
        //     }else {
        //         modelAndView.addObject("worker", this.workerService.loadWorkerByEmail(email));
        //         modelAndView.setViewName("career-home");
        //     }
        // }

        return modelAndView;
    }

    @GetMapping("career-home-for-noobs")
    private ModelAndView getNoobHome(ModelAndView modelAndView) {
        modelAndView.setViewName("career-home-for-noobs");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        modelAndView.addObject("worker", this.workerService.loadWorkerByEmail(email));
        return modelAndView;

    }

    @GetMapping("career-my-profile-for-noobs")
    public ModelAndView getMyProfile(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-profile-for-noobs");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        email="petio@abv.bg";
        Worker worker = this.workerService.loadWorkerByEmail(email);
        modelAndView.addObject("worker", worker);
        return modelAndView;

    }

    @GetMapping("/career-my-profile")
    public ModelAndView careerMyProfile(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-profile");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        email="petio@abv.bg";
        Worker worker = this.workerService.loadWorkerByEmail(email);
        modelAndView.addObject("worker", worker);
        return modelAndView;
    }

    @GetMapping("career-my-jobs")
    public ModelAndView getMyJobs(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-job");

        //  org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //  String email = auth.getName();
        List<Job> allJobs = this.jobService.findAllAvailable();
        List<Job> availableJobs = findAvailableJobs(allJobs);
        modelAndView.addObject("jobsAvailable", availableJobs);
        return modelAndView;

    }

    private List<Job> findAvailableJobs(List<Job> allJobs) {
        List<Job> availableJobs = new ArrayList<>();
        for (Job job : allJobs) {
            if (job.isAvailable()) {
                availableJobs.add(job);
            }
        }
        return availableJobs;
    }

    @PostMapping("/apply-for-job")
    public ModelAndView applyForProjects(@RequestParam("jobId") String jobId, ModelAndView modelAndView) {
        modelAndView.setViewName("career-home-for-noobs");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String ceko = "marto@abv.bg";
        Worker worker = this.workerService.loadWorkerByEmail(ceko);
        worker.setHassApplied(true);
        worker.setJob(this.jobService.findJobById(jobId));
        Job job = this.jobService.findJobById(jobId);
        job.getWorkerList().add(worker);
        this.jobService.saveJob(job);
        return modelAndView;
    }

    @GetMapping("/career-my-projects")
    public ModelAndView carerMyProjects(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-projects");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String ceko = "ceko@abv.bg";
        String id = "b8c4fce5-8670-473a-8d90-8d3008b44931";
        Project project = this.projectService.findProjectById(id);
        Worker worker = this.workerService.loadWorkerByEmail(ceko);
        project.getWorkerList().add(worker);
        worker.getProjectList().add(project);
        modelAndView.addObject("projects", project);

        return modelAndView;
    }


}
