package org.softuni.work.areas.admin.services.clasess;

import org.softuni.work.areas.admin.services.interfaces.AdminService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.roles.entities.Role;
import org.softuni.work.areas.roles.services.interfaces.RoleService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.edit.WorkerAdminBindingModel;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final WorkerService workerService;
    private final ProjectService projectService;
    private final RoleService roleService;

    public AdminServiceImpl(WorkerService workerService, ProjectService projectService, RoleService roleService) {
        this.workerService = workerService;
        this.projectService = projectService;
        this.roleService = roleService;
    }

    @Override
    public void getAdminHome(ModelAndView modelAndView) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.workerService.loadWorkerByEmail(email);
        modelAndView.addObject("admin", worker);
    }

    @Override
    public void getListWithProjects(ModelAndView modelAndView) {
        List<Project> projects = this.projectService.findAllProjects();
        modelAndView.addObject("allProjects", projects);
    }

    @Override
    public void editWorker(@RequestParam("workerId") String workerId, WorkerAdminBindingModel bindingModel) {
        Worker worker = this.workerService.findById(workerId);
        worker.setFullName(bindingModel.getFullName());
        worker.setEmail(bindingModel.getEmail());
        Role role = this.roleService.getRoleId(bindingModel.getRoleId());
        worker.getRoles().clear();
        worker.getRoles().add(role);
        this.workerService.saveEditedWorker(worker);
    }

    @Override
    public void getEditWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
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
    }

    @Override
    public void getListWithWorkers(ModelAndView modelAndView) {
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
    }

}
