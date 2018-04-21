package org.softuni.work.areas.jobs.service.clases;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.jobs.entities.Job;
import org.softuni.work.areas.jobs.models.JobBindingModel;
import org.softuni.work.areas.jobs.repository.JobRepository;
import org.softuni.work.areas.jobs.service.interfaces.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final ModelMapper mapper;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, ModelMapper mapper) {
        this.jobRepository = jobRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(JobBindingModel bindingModel) {
        Job job = this.mapper.map(bindingModel, Job.class);
        this.jobRepository.save(job);
    }

    @Override
    public List<Job> findAllAvailable() {
        return this.jobRepository.findAll();
    }

    @Override
    public void saveJob(Job job) {
        this.jobRepository.save(job);
    }

    @Override
    public Job findJobById(String id) {
        return this.jobRepository.findFirstById(id);

    }
}
