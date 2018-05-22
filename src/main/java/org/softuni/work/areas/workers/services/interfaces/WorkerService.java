package org.softuni.work.areas.workers.services.interfaces;

import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.models.edit.WorkerAdminBindingModel;
import org.softuni.work.areas.workers.models.edit.WorkerEditBindingModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

public interface WorkerService {
    Worker loadWorkerByEmail(String email);

    void register(WorkerRegisterBindingModel bindingModel);

    void login(WorkerLoginBindingModel bindingModel) throws Exception;

    Worker findById(String id);

    void saveEditedWorker(Worker worker);

    Worker editWorker(WorkerEditBindingModel bindingModel);

    void deleteById(String id);

    List<Worker> findAllWorkers();

    ModelAndView getModelAndView(Model model, ModelAndView modelAndView);

    ModelAndView getCareerRegister(Model model);

    ModelAndView getRegisterPropertyAndRegisterAnCareer(@Valid WorkerRegisterBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes);

    ModelAndView getHome(ModelAndView modelAndView);

    ModelAndView getRoleAndLoadHomePage(ModelAndView modelAndView, Model model);

    ModelAndView getProfileOfTheLogedInWorker(ModelAndView modelAndView);

    ModelAndView getLogedInCareerWithJob(ModelAndView modelAndView);

    ModelAndView getJobsOfTheLogedInWorker(ModelAndView modelAndView);

    ModelAndView applyForAJob(@RequestParam("jobId") String jobId, ModelAndView modelAndView);

    ModelAndView getMyProjects(ModelAndView modelAndView);


}
