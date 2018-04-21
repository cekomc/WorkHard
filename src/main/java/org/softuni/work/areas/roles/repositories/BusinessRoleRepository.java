package org.softuni.work.areas.roles.repositories;

import org.softuni.work.areas.roles.entities.BusinessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRoleRepository extends JpaRepository<BusinessRole, String> {
    BusinessRole findFirstByAuthority(String authority);
}
