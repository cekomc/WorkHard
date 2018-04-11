package org.softuni.work.areas.workers.services.interfaces;

import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;

public interface WorkerService {
    void register(WorkerRegisterBindingModel bindingModel);

    void login(WorkerLoginBindingModel bindingModel) throws Exception;
}
