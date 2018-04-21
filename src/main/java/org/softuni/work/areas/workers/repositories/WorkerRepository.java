package org.softuni.work.areas.workers.repositories;

import org.softuni.work.areas.workers.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, String>{
    Worker findFirstByEmail(String email);

    Worker findFirstById(String id);


}
