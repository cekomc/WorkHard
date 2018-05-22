package org.softuni.work.areas.admin.services.interfaces;

import org.softuni.work.areas.workers.models.edit.WorkerAdminBindingModel;
import org.springframework.web.servlet.ModelAndView;

public interface AdminService {
    void getAdminHome(ModelAndView modelAndView);

    void getListWithWorkers(ModelAndView modelAndView);

    void getListWithProjects(ModelAndView modelAndView);

    void getEditWorker( String workerId, ModelAndView modelAndView);

    void editWorker( String workerId, WorkerAdminBindingModel bindingModel);

}
