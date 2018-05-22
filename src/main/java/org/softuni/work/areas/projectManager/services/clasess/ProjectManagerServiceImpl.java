package org.softuni.work.areas.projectManager.services.clasess;

import org.softuni.work.areas.jobs.models.JobBindingModel;
import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.softuni.work.areas.projectManager.repositories.ProjectManagerRepository;
import org.softuni.work.areas.projectManager.services.interfaces.ProjectManagerService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.models.ProjectEditBindingModel;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.edit.WorkerEditBindingModel;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {
    private final ProjectManagerRepository repository;
    private final ProjectService projectService;
    private final WorkerService workerService;
    private final JobService jobService;




    @Autowired
    public ProjectManagerServiceImpl(ProjectManagerRepository repository, ProjectService projectService, WorkerService workerService, JobService jobService) {
        this.repository = repository;
        this.projectService = projectService;
        this.workerService = workerService;
        this.jobService = jobService;
    }

    @Override
    public List<Worker> listAllWorkers() {
        return this.repository.findAll();
    }

    @Override
    public ModelAndView getListWithAllProjects(ModelAndView modelAndView) {
        modelAndView.setViewName("project-manager-list-projects");
        List<Project> allProjects = this.projectService.findAllProjects();
        List<Project> unasignedProject = getAsignedFalseProjects(allProjects);
        List<Project> asignedProject = getAsignedTrueProjects(allProjects);
        modelAndView.addObject("assignProjects", asignedProject);
        modelAndView.addObject("unAssignedProjects", unasignedProject);
        return modelAndView;
    }

    @Override
    public void editProject(@RequestParam("projectId") String projectId, ModelAndView modelAndView) {
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
    }

    @Override
    public void doEditProject(@RequestParam("projectId") String projectId, ProjectEditBindingModel bindingModel) {
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
    }

    @Override
    public void doEditWorker(@RequestParam("workerId") String workerId, WorkerEditBindingModel bindingModel) {
        Worker worker = this.workerService.findById(workerId);
        worker.setFullName(bindingModel.getFullName());
        worker.setEmail(bindingModel.getEmail());
        worker.setPassword(bindingModel.getPassword());
        this.workerService.saveEditedWorker(worker);
    }

    @Override
    public void getWorkers(ModelAndView modelAndView) {
        List<Worker> appliedWorkers = findAllApplies(listAllWorkers());
        List<Worker> workersWithJob = findAllWorkersWithJob(listAllWorkers());
        modelAndView.addObject("workers", workersWithJob);
        modelAndView.addObject("applyForWorkers", appliedWorkers);
    }

    @Override
    public void aproveWorker(@RequestParam("workerId") String workerId) {
        Worker worker = this.workerService.findById(workerId);
        worker.setAproved(true);
        this.workerService.saveEditedWorker(worker);
    }

    @Override
    public void editWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        Worker worker = this.workerService.findById(workerId);
        modelAndView.addObject("workerCurrentInformation", worker);
    }

    @Override
    public void getJobCreate(Model model) {
        if (!model.containsAttribute("job")) {
            model.addAttribute("job",
                    new JobBindingModel());
        }
    }

    @Override
    public void createJob(@Valid JobBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.job", bindingResult);
            redirectAttributes.addFlashAttribute("job", bindingModel);

            modelAndView.setViewName("redirect:/project-manager-create-job");
        } else {
            modelAndView.setViewName("redirect:/project-manager-create-job");
            bindingModel.setAvailable(true);
            this.jobService.create(bindingModel);
        }
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

    private List<Worker> findAllWorkersWithJob(List<Worker> workers) {
        List<Worker> workersWithJob = new ArrayList<>();
        for (Worker worker : workers) {
            if (worker.isHassApplied() && worker.isAproved() && worker.getJob() != null) {
                workersWithJob.add(worker);
            }
        }
        return workersWithJob;
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
