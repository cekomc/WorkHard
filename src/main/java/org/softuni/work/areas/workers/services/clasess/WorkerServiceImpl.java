package org.softuni.work.areas.workers.services.clasess;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.jobs.entities.Job;
import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.softuni.work.areas.roles.entities.Role;
import org.softuni.work.areas.roles.services.interfaces.RoleService;
import org.softuni.work.areas.workers.entities.Worker;
import org.softuni.work.areas.workers.models.WorkerLoginBindingModel;
import org.softuni.work.areas.workers.models.WorkerRegisterBindingModel;
import org.softuni.work.areas.workers.models.edit.WorkerAdminBindingModel;
import org.softuni.work.areas.workers.models.edit.WorkerEditBindingModel;
import org.softuni.work.areas.workers.repositories.WorkerRepository;
import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService, UserDetailsService {
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final WorkerRepository workerRepository;
    private final JobService jobService;
    private final ProjectService projectService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private String message;


    @Autowired
    public WorkerServiceImpl(RoleService roleService, ModelMapper mapper,
                             WorkerRepository workerRepository, JobService jobService, ProjectService projectService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.mapper = mapper;
        this.workerRepository = workerRepository;
        this.jobService = jobService;
        this.projectService = projectService;
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
        if (worker.getPassword().length() < 20) {
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
    public ModelAndView getRegisterPropertyAndRegisterAnCareer(@Valid WorkerRegisterBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workerInput", bindingResult);
            redirectAttributes.addFlashAttribute("workerInput", bindingModel);

            modelAndView.setViewName("redirect:/career-register");
        } else {
            modelAndView.setViewName("redirect:/career-login");
            this.register(bindingModel);
            message = "Worker successful registered";
        }

        return modelAndView;
    }

    @Override
    public ModelAndView getCareerRegister(Model model) {
        if (!model.containsAttribute("workerInput")) {
            model.addAttribute("workerInput",
                    new WorkerRegisterBindingModel());
        }
        return new ModelAndView("career-register");
    }

    @Override
    public ModelAndView getModelAndView(Model model, ModelAndView modelAndView) {
        modelAndView.setViewName("/career-login");
        if (!model.containsAttribute("workerInput"))
            model.addAttribute("workerInput", new WorkerLoginBindingModel());
        if (!(message == null)) {
            model.addAttribute("message", message);
        }
        return modelAndView;
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

    @Override
    public ModelAndView getHome(ModelAndView modelAndView) {
        modelAndView.setViewName("career-home");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        modelAndView.addObject("worker", this.loadWorkerByEmail(email));

        return modelAndView;
    }

    @Override
    public ModelAndView getRoleAndLoadHomePage(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("career-home-for-noobs");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.loadWorkerByEmail(email);
        for (Role role : worker.getRoles()) {
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                modelAndView.setViewName("redirect:/admin-home");
            } else if (role.getAuthority().equals("ROLE_PROJECT_MANAGER")) {
                modelAndView.setViewName("redirect:/project-manager-home");
            } else if (worker.isAproved()) {
                modelAndView.setViewName("redirect:/career-home");
            }
        }
        modelAndView.addObject("worker", this.loadWorkerByEmail(email));
        if (!(message == null)) {
            model.addAttribute("message", message);
        }
        return modelAndView;
    }

    @Override
    public ModelAndView getProfileOfTheLogedInWorker(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-profile-for-noobs");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.loadWorkerByEmail(email);
        modelAndView.addObject("worker", worker);
        return modelAndView;
    }

    @Override
    public ModelAndView getLogedInCareerWithJob(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-profile");

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.loadWorkerByEmail(email);
        modelAndView.addObject("worker", worker);
        return modelAndView;
    }

    @Override
    public ModelAndView getJobsOfTheLogedInWorker(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-job");

        //  org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //  String email = auth.getName();
        List<Job> allJobs = this.jobService.findAllAvailable();
        List<Job> availableJobs = findAvailableJobs(allJobs);
        modelAndView.addObject("jobsAvailable", availableJobs);
        return modelAndView;
    }

    private List<Job> findAvailableJobs(List<Job> allJobs) {
        List<Job> availableJobs = new ArrayList<>();
        for (Job job : allJobs) {
            if (job.isAvailable()) {
                availableJobs.add(job);
            }
        }
        return availableJobs;
    }

    @Override
    public ModelAndView applyForAJob(@RequestParam("jobId") String jobId, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/career-home-for-noobs");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.loadWorkerByEmail(email);
        worker.setHassApplied(true);
        worker.setJob(this.jobService.findJobById(jobId));
        Job job = this.jobService.findJobById(jobId);
        job.getWorkerList().add(worker);
        this.jobService.saveJob(job);
        this.saveEditedWorker(worker);
        this.message = "Successful applied for job";
        return modelAndView;
    }

    @Override
    public ModelAndView getMyProjects(ModelAndView modelAndView) {
        modelAndView.setViewName("career-my-projects");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Worker worker = this.loadWorkerByEmail(email);
        List<Project> project = new ArrayList<>();
        project.addAll(worker.getProjectList());
        modelAndView.addObject("projects", project);

        return modelAndView;
    }
}
