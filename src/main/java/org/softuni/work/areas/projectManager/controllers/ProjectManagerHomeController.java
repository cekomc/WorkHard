package org.softuni.work.areas.projectManager.controllers;

import org.softuni.work.areas.jobs.models.JobBindingModel;
import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.softuni.work.areas.projectManager.services.interfaces.ProjectManagerService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.models.ProjectEditBindingModel;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.edit.WorkerEditBindingModel;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectManagerHomeController {
    private final ProjectManagerService service;
    private final ProjectService projectService;
    private final JobService jobService;
    private final WorkerService workerService;

    @Autowired
    public ProjectManagerHomeController(ProjectManagerService service, ProjectService projectService, JobService jobService, WorkerService workerService) {
        this.service = service;
        this.projectService = projectService;
        this.jobService = jobService;
        this.workerService = workerService;
    }

    @GetMapping("/project-manager-home")
    public ModelAndView projectManagerHome() {
        return new ModelAndView("project-manager-home");
    }

    @GetMapping("/project-manager-list-projects")
    public ModelAndView projectManagerListProjects(ModelAndView modelAndView) {
        return this.service.getListWithAllProjects(modelAndView);
    }

    @GetMapping("/project-manager-delete-project")
    public ModelAndView projectManagerDeleteProject(@RequestParam("projectId") String projectId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/project-manager-list-projects");
        this.projectService.deleteProject(projectId);
        return modelAndView;
    }

    @GetMapping("/project-manager-edit-project")
    public ModelAndView projectManagerEditProject(@RequestParam("projectId") String projectId, ModelAndView modelAndView) {
        modelAndView.setViewName("project-manager-edit-project");
        service.editProject(projectId, modelAndView);
        return modelAndView;
    }

    @PostMapping("/project-manager-edit-project")
    public ModelAndView projectManagerDoEditProject(@RequestParam("projectId") String projectId, ModelAndView modelAndView, ProjectEditBindingModel bindingModel) {
        modelAndView.setViewName("redirect:/project-manager-list-projects");
        service.doEditProject(projectId, bindingModel);
        return modelAndView;
    }


    @GetMapping("/project-manager-workers")
    public ModelAndView projectManagerWorkers(ModelAndView modelAndView) {
        modelAndView.setViewName("project-manager-workers");
        service.getWorkers(modelAndView);
        return modelAndView;
    }



    @PostMapping("/project-manager-aprove-worker")
    public ModelAndView projectManagerAproveWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/project-manager-workers");
        service.aproveWorker(workerId);
        return modelAndView;
    }


    @GetMapping("/project-manager-edit-worker")
    public ModelAndView projectManagerEditWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("/project-manager-edit-worker");
        service.editWorker(workerId, modelAndView);
        return modelAndView;
    }

    @PostMapping("/project-manager-edit-worker")
    public ModelAndView projectManagerDoEditWorker(@RequestParam("workerId") String workerId, WorkerEditBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/project-manager-workers");
        service.doEditWorker(workerId, bindingModel);
        return modelAndView;
    }



    @PostMapping("/project-manager-delete-worker")
    public ModelAndView projectManagerDoDeleteWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/project-manager-workers");
        this.workerService.deleteById(workerId);
        return modelAndView;
    }


    @GetMapping("/project-manager-create-job")
    public ModelAndView projectManagerCreateJob(Model model) {
        service.getJobCreate(model);
        return new ModelAndView("project-manager-create-job");
    }



    @PostMapping("/project-manager-create-job")
    public ModelAndView projectManagerCreateJobPost(@Valid JobBindingModel bindingModel,
                                                    BindingResult bindingResult,
                                                    ModelAndView modelAndView,
                                                    RedirectAttributes redirectAttributes) {
        service.createJob(bindingModel, bindingResult, modelAndView, redirectAttributes);
        return modelAndView;
    }

}
