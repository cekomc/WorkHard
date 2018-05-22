package org.softuni.work.areas.projectManager.services.interfaces;

import org.softuni.work.areas.jobs.models.JobBindingModel;
import org.softuni.work.areas.projects.models.ProjectEditBindingModel;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.edit.WorkerEditBindingModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ProjectManagerService {
    List<Worker> listAllWorkers();

    ModelAndView getListWithAllProjects(ModelAndView modelAndView);

    void editProject(String id, ModelAndView modelAndView);

    void doEditProject(String projectId, ProjectEditBindingModel bindingModel);

    void doEditWorker(String workerId, WorkerEditBindingModel bindingModel);

    void getWorkers(ModelAndView modelAndView);

    void aproveWorker(String workerId);

    void editWorker(String workerId, ModelAndView modelAndView);

    void getJobCreate(Model model);

    void createJob(JobBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes);
}
