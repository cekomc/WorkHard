package org.softuni.work.areas.workers.controllers;

import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


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
        return this.workerService.getHome(modelAndView);
    }


    @GetMapping("career-home-for-noobs")
    private ModelAndView getNoobHome(ModelAndView modelAndView, Model model) {
        return this.workerService.getRoleAndLoadHomePage(modelAndView, model);

    }

    @GetMapping("career-my-profile-for-noobs")
    public ModelAndView getMyProfile(ModelAndView modelAndView) {
        return this.workerService.getProfileOfTheLogedInWorker(modelAndView);

    }

    @GetMapping("/career-my-profile")
    public ModelAndView careerMyProfile(ModelAndView modelAndView) {
        return this.workerService.getLogedInCareerWithJob(modelAndView);
    }

    @GetMapping("career-my-jobs")
    public ModelAndView getMyJobs(ModelAndView modelAndView, Model model) {
        return this.workerService.getJobsOfTheLogedInWorker(modelAndView);

    }


    @PostMapping("/apply-for-job")
    public ModelAndView applyForProjects(@RequestParam("jobId") String jobId, ModelAndView modelAndView) {
        return this.workerService.applyForAJob(jobId, modelAndView);
    }



    @GetMapping("/career-my-projects")
    public ModelAndView carerMyProjects(ModelAndView modelAndView) {
        return this.workerService.getMyProjects(modelAndView);
    }


}
