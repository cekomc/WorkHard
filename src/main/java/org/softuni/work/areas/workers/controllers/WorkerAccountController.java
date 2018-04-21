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

import javax.validation.Valid;


@Controller
public class WorkerAccountController {
    private final WorkerService workerService;

    @Autowired
    public WorkerAccountController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/career-login")
    public ModelAndView careerLogin(Model model) {
        if (!model.containsAttribute("workerInput"))
            model.addAttribute("workerInput", new BusinessPartnerLoginBindingModel());
        return new ModelAndView("career-login");
    }

  // @PostMapping("/career-login")
  // public ModelAndView loginConfirm(@Valid WorkerLoginBindingModel bindingModel,
  //                                  BindingResult bindingResult,
  //                                  ModelAndView modelAndView,
  //                                  Model model,
  //                                  RedirectAttributes redirectAttributes) throws Exception {
  //     if (bindingResult.hasErrors()) {
  //         redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workerInput", bindingResult);
  //         redirectAttributes.addFlashAttribute("workerInput", bindingModel);

  //         modelAndView.setViewName("redirect:/career-login");
  //     }else{
  //         modelAndView.setViewName("redirect:/career-home");
  //     }
  //     try {
  //         this.workerService.login(bindingModel);

  //     } catch (Exception e) {
  //         String errorMsg =e.getMessage();
  //         model.addAttribute("errorMsg", errorMsg);
  //         modelAndView.setViewName("redirect:/career-login");

  //     }

  //     return modelAndView;
  // }

    @GetMapping("/career-register")
    public ModelAndView careerRegister(Model model) {
        if (!model.containsAttribute("workerInput")) {
            model.addAttribute("workerInput",
                    new WorkerRegisterBindingModel());
        }
        return new ModelAndView("career-register");
    }

    @PostMapping("/career-register")
    public ModelAndView registerConfirm(@Valid WorkerRegisterBindingModel bindingModel,
                                        BindingResult bindingResult,
                                        ModelAndView modelAndView,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workerInput", bindingResult);
            redirectAttributes.addFlashAttribute("workerInput", bindingModel);

            modelAndView.setViewName("redirect:/career-register");
        } else {
            modelAndView.setViewName("redirect:/career-login");
            this.workerService.register(bindingModel);
        }

        return modelAndView;
    }
}
