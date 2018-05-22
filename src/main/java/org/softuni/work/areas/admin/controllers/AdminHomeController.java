package org.softuni.work.areas.admin.controllers;

import org.softuni.work.areas.admin.services.interfaces.AdminService;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.roles.services.interfaces.RoleService;
import org.softuni.work.areas.workers.models.edit.WorkerAdminBindingModel;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminHomeController {
    private final WorkerService workerService;
    private final AdminService adminService;
    private final RoleService roleService;
    private final ProjectService projectService;

    public AdminHomeController(WorkerService workerService, AdminService adminService, RoleService roleService, ProjectService projectService) {
        this.workerService = workerService;
        this.adminService = adminService;
        this.roleService = roleService;
        this.projectService = projectService;
    }


    @GetMapping("/admin-home")
    public ModelAndView adminHome(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-home");
        adminService.getAdminHome(modelAndView);
        return modelAndView;
    }



    @GetMapping("/admin-workers")
    public ModelAndView adminWorkers(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-workers");
        adminService.getListWithWorkers(modelAndView);
        return modelAndView;
    }

    @GetMapping("/admin-all-projects")
    public ModelAndView adminAllProjects(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-all-projects");
        adminService.getListWithProjects(modelAndView);
        return modelAndView;
    }


    @GetMapping("/admin-edit-worker")
    public ModelAndView adminEditWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("/admin-worker-edit");
        adminService.getEditWorker(workerId, modelAndView);
        return modelAndView;
    }


    @PostMapping("/admin-edit-worker")
    public ModelAndView adminDoEditWorker(@RequestParam("workerId") String workerId, WorkerAdminBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/admin-workers");
        adminService.editWorker(workerId, bindingModel);
        return modelAndView;
    }

    @PostMapping("/admin-delete-worker")
    public ModelAndView adminDeleteWorker(@RequestParam("workerId") String workerId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/admin-workers");
        this.workerService.deleteById(workerId);
        return modelAndView;
    }
}
