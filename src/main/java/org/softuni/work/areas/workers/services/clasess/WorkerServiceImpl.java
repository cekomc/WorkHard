package org.softuni.work.areas.workers.services.clasess;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.roles.entities.Role;
import org.softuni.work.areas.roles.repositories.RoleRepository;
import org.softuni.work.areas.roles.services.interfaces.RoleService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.models.edit.WorkerEditBindingModel;
import org.softuni.work.areas.workers.repositories.WorkerRepository;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService, UserDetailsService {
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final WorkerRepository workerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public WorkerServiceImpl(RoleService roleService, ModelMapper mapper,
                             WorkerRepository workerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.mapper = mapper;
        this.workerRepository = workerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void register(WorkerRegisterBindingModel bindingModel) {
        Worker worker = this.mapper.map(bindingModel, Worker.class);

        worker.setPassword(this.bCryptPasswordEncoder.encode(worker.getPassword()));

        Role role = this.roleService.getRole("ROLE_WORKER");
        worker.getRoles().add(role);

        this.roleService.create(role);
        this.workerRepository.save(worker);
    }

    @Override
    public void login(WorkerLoginBindingModel bindingModel) throws Exception {
        Worker check = this.workerRepository.findFirstByEmail(bindingModel.getEmail());
        if (check == null) {
            throw new Exception("Email not found");
        } else if (!bCryptPasswordEncoder.matches(bindingModel.getPassword(), check.getPassword())) {
            throw new Exception("Password not found");
        }

    }

    @Override
    public Worker findById(String id) {
        return this.workerRepository.findFirstById(id);
    }

    @Override
    public void saveEditedWorker(Worker worker) {
        if(worker.getPassword().length()<20){
            worker.setPassword(this.bCryptPasswordEncoder.encode(worker.getPassword()));
        }
       this.workerRepository.save(worker);
    }

    @Override
    public Worker editWorker(WorkerEditBindingModel bindingModel) {
        Worker worker = this.mapper.map(bindingModel, Worker.class);
        return worker;
    }

    @Override
    public void deleteById(String id) {
        this.workerRepository.deleteById(id);
    }

    @Override
    public List<Worker> findAllWorkers() {
        return this.workerRepository.findAll();
    }


    @Override
    public Worker loadWorkerByEmail(String email) {
        Worker worker = this.workerRepository.findFirstByEmail(email);
        return worker;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Worker worker = this.workerRepository.findFirstByEmail(email);
        if (worker == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<GrantedAuthority> roles = worker.getRoles()
                .stream().map(r -> new SimpleGrantedAuthority(r.getAuthority()))
                .collect(Collectors.toSet());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                worker.getEmail(),
                worker.getPassword(),
                roles
        );

        return userDetails;
    }


}
