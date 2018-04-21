package org.softuni.work.areas.roles.services.clases;

import org.softuni.work.areas.roles.entities.Role;
import org.softuni.work.areas.roles.repositories.RoleRepository;
import org.softuni.work.areas.roles.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRole(String authority) {
        return this.roleRepository.findFirstByAuthority(authority);
    }

    @Override
    public Role create(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role getRoleId(String id) {
        return this.roleRepository.findFirstById(id);
    }
}
