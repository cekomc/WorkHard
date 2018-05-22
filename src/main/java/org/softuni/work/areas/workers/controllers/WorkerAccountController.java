package org.softuni.work.areas.workers.controllers;

import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class WorkerAccountController {
    private final WorkerService workerService;

    @Autowired
    public WorkerAccountController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/career-login")
    public ModelAndView careerLogin(Model model, ModelAndView modelAndView) {
       return this.workerService.getModelAndView(model, modelAndView);
    }


    @GetMapping("/career-register")
    public ModelAndView careerRegister(Model model) {
        return this.workerService.getCareerRegister(model);
    }

    @PostMapping("/career-register")
    public ModelAndView registerConfirm(@Valid WorkerRegisterBindingModel bindingModel,
                                        BindingResult bindingResult,
                                        Model model,
                                        ModelAndView modelAndView,
                                        RedirectAttributes redirectAttributes) {
        return this.workerService.getRegisterPropertyAndRegisterAnCareer(bindingModel, bindingResult, modelAndView, redirectAttributes);
    }


}
