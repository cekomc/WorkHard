package org.softuni.work.areas.projectManager.repositories;

import org.softuni.work.areas.workers.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectManagerRepository extends JpaRepository<Worker, String>{

}
