package org.softuni.work.areas.roles.repositories;

import org.softuni.work.areas.roles.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findFirstByAuthority(String authority);

    Role findFirstById(String id);
}
