package org.softuni.work.areas.workers.services.clasess;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.repositories.WorkerRepository;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService{
    private final ModelMapper mapper;
    private final WorkerRepository workerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public WorkerServiceImpl(ModelMapper mapper, WorkerRepository workerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mapper = mapper;
        this.workerRepository = workerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void register(WorkerRegisterBindingModel bindingModel) {
        Worker worker = this.mapper.map(bindingModel, Worker.class);

        worker.setPassword(this.bCryptPasswordEncoder.encode(worker.getPassword()));

        this.workerRepository.save(worker);
    }

    @Override
    public void login(WorkerLoginBindingModel bindingModel) throws Exception {
       Worker check = this.workerRepository.findFirstByEmail(bindingModel.getEmail());
       if(check==null){
           throw new Exception("Email not found");
       }else if(!bCryptPasswordEncoder.matches(bindingModel.getPassword(), check.getPassword())){
           throw new Exception("Password not found");
       }

    }
}
