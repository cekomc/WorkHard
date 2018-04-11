package org.softuni.work.areas.workers.services.clasess;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.repositories.WorkerRepository;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService{
    private final ModelMapper mapper;
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerServiceImpl(ModelMapper mapper, WorkerRepository workerRepository) {
        this.mapper = mapper;
        this.workerRepository = workerRepository;
    }


    @Override
    public void register(WorkerRegisterBindingModel bindingModel) {
        Worker worker = this.mapper.map(bindingModel, Worker.class);
        this.workerRepository.save(worker);
    }

    @Override
    public void login(WorkerLoginBindingModel bindingModel) throws Exception {
       Worker check = this.workerRepository.findFirstByEmail(bindingModel.getEmail());
       if(check==null){
           throw new Exception("Email not found");
       }else if(!check.getPassword().equals(bindingModel.getPassword())){
           throw new Exception("Password not found");
       }

    }
}
