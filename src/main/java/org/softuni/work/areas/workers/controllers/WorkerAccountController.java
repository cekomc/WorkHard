package org.softuni.work.areas.workers.controllers;

import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WorkerAccountController {
    private final WorkerService workerService;

    @Autowired
    public WorkerAccountController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/career-login")
    public ModelAndView careerLogin() {
        return new ModelAndView("career-login");
    }

    @PostMapping("/career-login")
    public ModelAndView loginConfirm(@ModelAttribute WorkerLoginBindingModel bindingModel, ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("redirect:/career-home");
        this.workerService.login(bindingModel);
        return modelAndView;
    }

    @GetMapping("/career-register")
    public ModelAndView careerRegister() {
        return new ModelAndView("career-register");
    }

    @PostMapping("/career-register")
    public ModelAndView registerConfirm(@ModelAttribute WorkerRegisterBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/career-login");
        this.workerService.register(bindingModel);
        return modelAndView;
    }
}
