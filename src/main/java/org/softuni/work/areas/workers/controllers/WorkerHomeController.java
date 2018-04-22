package org.softuni.work.areas.workers.controllers;

import org.softuni.work.areas.jobs.entities.Job;
import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.roles.entities.Role;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private String message;

    @Autowired
    public WorkerHomeController(WorkerService workerService, ProjectService projectService, JobService jobService) {
        this.workerService = workerService;
        this.projectService = projectService;
        this.jobService = jobService;
    }


    @GetMapping("/career-home")
    private ModelAndView careerHome(ModelAndView modelAndView) {
        modelAndView.setViewName("career-home");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        modelAndView.addObject("worker", this.workerService.loadWorkerByEmail(email));

        return modelAndView;
    }

    @GetMapping("career-home-for-noobs")
    private ModelAndView getNoobHome(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("career-home-for-noobs");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.workerService.loadWorkerByEmail(email);
        for (Role role : worker.getRoles()) {
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                modelAndView.setViewName("redirect:/admin-home");
            } else if (role.getAuthority().equals("ROLE_PROJECT_MANAGER")) {
                modelAndView.setViewName("redirect:/project-manager-home");
            } else if (worker.isAproved()) {
                modelAndView.setViewName("redirect:/career-home");
            }
        }
        modelAndView.addObject("worker", this.workerService.loadWorkerByEmail(email));
        if (!(message == null)) {
            model.addAttribute("message", message);
        }
        return modelAndView;

    }

    @GetMapping("career-my-profile-for-noobs")
    public ModelAndView getMyProfile(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-profile-for-noobs");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.workerService.loadWorkerByEmail(email);
        modelAndView.addObject("worker", worker);
        return modelAndView;

    }

    @GetMapping("/career-my-profile")
    public ModelAndView careerMyProfile(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-profile");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.workerService.loadWorkerByEmail(email);
        modelAndView.addObject("worker", worker);
        return modelAndView;
    }

    @GetMapping("career-my-jobs")
    public ModelAndView getMyJobs(ModelAndView modelAndView, Model model) {
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
        modelAndView.setViewName("redirect:/career-home-for-noobs");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.workerService.loadWorkerByEmail(email);
        worker.setHassApplied(true);
        worker.setJob(this.jobService.findJobById(jobId));
        Job job = this.jobService.findJobById(jobId);
        job.getWorkerList().add(worker);
        this.jobService.saveJob(job);
        this.workerService.saveEditedWorker(worker);
        this.message = "Successful applied for job";
        return modelAndView;
    }

    @GetMapping("/career-my-projects")
    public ModelAndView carerMyProjects(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-projects");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.workerService.loadWorkerByEmail(email);
        List<Project> project = new ArrayList<>();
        project.addAll(worker.getProjectList());
        modelAndView.addObject("projects", project);

        return modelAndView;
    }

}
