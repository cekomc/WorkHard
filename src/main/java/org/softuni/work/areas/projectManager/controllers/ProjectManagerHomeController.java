package org.softuni.work.areas.projectManager.controllers;

import ch.qos.logback.core.joran.conditional.ElseAction;
import org.softuni.work.areas.jobs.models.JobBindingModel;
import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.softuni.work.areas.projectManager.services.interfaces.ProjectManagerService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.models.ProjectEditBindingModel;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
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
        modelAndView.setViewName("project-manager-list-projects");
        List<Project> allProjects = this.projectService.findAllProjects();
        List<Project> unasignedProject = getAsignedFalseProjects(allProjects);
        List<Project> asignedProject = getAsignedTrueProjects(allProjects);
        modelAndView.addObject("assignProjects", asignedProject);
        modelAndView.addObject("unAssignedProjects", unasignedProject);
        return modelAndView;
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
        Project project = this.projectService.findProjectById(projectId);
        List<Worker> workers = this.workerService.findAllWorkers();
        List<Worker> workersWithJobs = loadWorkersWithJob(workers);
        ProjectEditBindingModel projectEditBindingModel = new ProjectEditBindingModel();
        projectEditBindingModel.setId(project.getId());
        projectEditBindingModel.setName(project.getName());
        projectEditBindingModel.setDescription(project.getDescription());
        projectEditBindingModel.setLink(project.getLink());
        modelAndView.addObject("currentProject", projectEditBindingModel);
        modelAndView.addObject("workersWithJobs", workersWithJobs);
        return modelAndView;
    }

    @PostMapping("/project-manager-edit-project")
    public ModelAndView projectManagerDoEditProject(@RequestParam("projectId") String projectId, ModelAndView modelAndView, ProjectEditBindingModel bindingModel) {
        modelAndView.setViewName("redirect:/project-manager-list-projects");
        Project project = this.projectService.findProjectById(projectId);
        project.setName(bindingModel.getName());
        project.setDescription(bindingModel.getDescription());
        project.setLink(bindingModel.getLink());
        Worker curentWorker = new Worker();
        for (String id : bindingModel.getWorkersId()) {
            curentWorker = (this.workerService.findById(id));
            project.getWorkerList().add(curentWorker);
        }
        project.setAssigned(true);
        this.projectService.saveEditedProject(project);
        return modelAndView;
    }

    @PostMapping("/project-manager-edit-worker")
    public ModelAndView projectManagerDoEditWorker(@RequestParam("workerId") String workerId, WorkerEditBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/project-manager-workers");
        Worker worker = this.workerService.findById(workerId);
        worker.setFullName(bindingModel.getFullName());
        worker.setEmail(bindingModel.getEmail());
        worker.setPassword(bindingModel.getPassword());
        this.workerService.saveEditedWorker(worker);
        return modelAndView;
    }

    private List<Worker> loadWorkersWithJob(List<Worker> workers) {
        List<Worker> workersWithJobs = new ArrayList<>();
        for (Worker worker : workers) {
            if (null != worker.getJob()) {
                workersWithJobs.add(worker);
            }
        }
        return workersWithJobs;
    }

    @GetMapping("/project-manager-workers")
    public ModelAndView projectManagerWorkers(ModelAndView modelAndView) {
        modelAndView.setViewName("project-manager-workers");
        List<Worker> appliedWorkers = findAllApplies(this.service.listAllWorkers());
        List<Worker> workersWithJob = findAllWorkersWithJob(this.service.listAllWorkers());
        modelAndView.addObject("workers", workersWithJob);
        modelAndView.addObject("applyForWorkers", appliedWorkers);


        return modelAndView;
    }

    @PostMapping("/project-manager-aprove-worker")
    public ModelAndView projectManagerAproveWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/project-manager-workers");
        Worker worker = this.workerService.findById(workerId);
        worker.setAproved(true);
        this.workerService.saveEditedWorker(worker);

        return modelAndView;
    }


    @GetMapping("/project-manager-edit-worker")
    public ModelAndView projectManagerEditWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("/project-manager-edit-worker");
        Worker worker = this.workerService.findById(workerId);
        modelAndView.addObject("workerCurrentInformation", worker);
        return modelAndView;
    }

    @PostMapping("/project-manager-delete-worker")
    public ModelAndView projectManagerDoDeleteWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/project-manager-workers");
        this.workerService.deleteById(workerId);
        return modelAndView;
    }


    private List<Worker> findAllWorkersWithJob(List<Worker> workers) {
        List<Worker> workersWithJob = new ArrayList<>();
        for (Worker worker : workers) {
            if (worker.isHassApplied() && worker.isAproved() && worker.getJob()!=null) {
                workersWithJob.add(worker);
            }
        }
        return workersWithJob;
    }

    private List<Worker> findAllApplies(List<Worker> workers) {
        List<Worker> appliedWorkers = new ArrayList<>();
        for (Worker worker : workers) {
            if (worker.isHassApplied() && !worker.isAproved()) {
                appliedWorkers.add(worker);
            }
        }
        return appliedWorkers;
    }


    @GetMapping("/project-manager-create-job")
    public ModelAndView projectManagerCreateJob(Model model) {
        if (!model.containsAttribute("job")) {
            model.addAttribute("job",
                    new JobBindingModel());
        }
        return new ModelAndView("project-manager-create-job");
    }

    @PostMapping("/project-manager-create-job")
    public ModelAndView projectManagerCreateJobPost(@Valid JobBindingModel bindingModel,
                                                    BindingResult bindingResult,
                                                    ModelAndView modelAndView,
                                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.job", bindingResult);
            redirectAttributes.addFlashAttribute("job", bindingModel);

            modelAndView.setViewName("redirect:/project-manager-create-job");
        } else {
            modelAndView.setViewName("redirect:/project-manager-create-job");
            bindingModel.setAvailable(true);
            this.jobService.create(bindingModel);
        }

        return modelAndView;
    }


    public List<Project> getAsignedFalseProjects(List<Project> projectsList) {
        List<Project> notAssigned = new ArrayList<>();
        for (Project project : projectsList) {
            if (!project.isAssigned()) {
                notAssigned.add(project);
            }
        }
        return notAssigned;
    }

    public List<Project> getAsignedTrueProjects(List<Project> projectsList) {
        List<Project> asignedProject = new ArrayList<>();
        for (Project project : projectsList) {
            if (project.isAssigned()) {
                asignedProject.add(project);
            }
        }
        return asignedProject;
    }

}
