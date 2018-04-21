package org.softuni.work.areas.jobs.repository;

import org.softuni.work.areas.jobs.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
    Job findFirstById(String id);
}
