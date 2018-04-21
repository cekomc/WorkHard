package org.softuni.work.areas.workers.services.interfaces;

import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.models.edit.WorkerEditBindingModel;

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
}
