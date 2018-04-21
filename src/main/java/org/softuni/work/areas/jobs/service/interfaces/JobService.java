package org.softuni.work.areas.jobs.service.interfaces;

import org.softuni.work.areas.jobs.entities.Job;
import org.softuni.work.areas.jobs.models.JobBindingModel;

import java.util.List;

public interface JobService {
    void create(JobBindingModel bindingModel);

    List<Job> findAllAvailable();

    void saveJob(Job job);

    Job findJobById(String id);
}
