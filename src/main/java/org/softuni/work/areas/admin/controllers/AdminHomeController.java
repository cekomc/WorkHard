package org.softuni.work.areas.admin.controllers;

import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.roles.entities.Role;
import org.softuni.work.areas.roles.services.interfaces.RoleService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.edit.WorkerAdminBindingModel;
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
public class AdminHomeController {
    private final WorkerService workerService;
    private final RoleService roleService;
    private final ProjectService projectService;

    @Autowired
    public AdminHomeController(WorkerService workerService, RoleService roleService, ProjectService projectService) {
        this.workerService = workerService;
        this.roleService = roleService;
        this.projectService = projectService;
    }

    @GetMapping("/admin-home")
    public ModelAndView adminHome(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-home");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.workerService.loadWorkerByEmail(email);
        modelAndView.addObject("admin", worker);

        return modelAndView;
    }

    @GetMapping("/admin-workers")
    public ModelAndView adminWorkers(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-workers");
        List<Worker> allWorkers = this.workerService.findAllWorkers();
        List<Worker> workersWithJob = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        for (Worker worker : allWorkers) {
            if(null!=worker.getJob()){
                workersWithJob.add(worker);
            }
            if (worker.getRoles().isEmpty()) {
                Role role = new Role();
                role = this.roleService.getRoleId("c31125fa-430b-11e8-842f-0ed5f89f718b");
                worker.getRoles().add(role);
            }
            for (Role role : worker.getRoles()) {
                roles.add(role);
            }
        }
        modelAndView.addObject("workers", workersWithJob);
        modelAndView.addObject("roles", roles);
        return modelAndView;

    }

    @GetMapping("/admin-all-projects")
    public ModelAndView adminAllProjects(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-all-projects");
        List<Project> projects = this.projectService.findAllProjects();
        modelAndView.addObject("allProjects", projects);
        return modelAndView;
    }

    @GetMapping("/admin-edit-worker")
    public ModelAndView adminEditWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-worker-edit");
        Worker worker = this.workerService.findById(workerId);
        List<Role> allRoles = new ArrayList<>();
        allRoles.add(this.roleService.getRole("ROLE_WORKER"));
        allRoles.add(this.roleService.getRole("ROLE_PROJECT_MANAGER"));
        WorkerAdminBindingModel workerAdminBindingModel = new WorkerAdminBindingModel();
        workerAdminBindingModel.setId(worker.getId());
        workerAdminBindingModel.setFullName(worker.getFullName());
        workerAdminBindingModel.setEmail(worker.getEmail());
        workerAdminBindingModel.setFullName(worker.getFullName());
        modelAndView.addObject("workerCurrentInformation", workerAdminBindingModel);
        modelAndView.addObject("allRoles", allRoles);
        return modelAndView;
    }

    @PostMapping("/admin-edit-worker")
    public ModelAndView adminDoEditWorker(@RequestParam("workerId") String workerId, WorkerAdminBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/admin-workers");
        Worker worker = this.workerService.findById(workerId);
        worker.setFullName(bindingModel.getFullName());
        worker.setEmail(bindingModel.getEmail());
        Role role = this.roleService.getRoleId(bindingModel.getRoleId());
        worker.getRoles().clear();
        worker.getRoles().add(role);
        this.workerService.saveEditedWorker(worker);
        return modelAndView;
    }

    @PostMapping("/admin-delete-worker")
    public ModelAndView adminDeleteWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/admin-workers");
        this.workerService.deleteById(workerId);
        return modelAndView;
    }
}
