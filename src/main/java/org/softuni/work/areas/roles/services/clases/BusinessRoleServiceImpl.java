package org.softuni.work.areas.roles.services.clases;

import org.softuni.work.areas.roles.entities.BusinessRole;
import org.softuni.work.areas.roles.repositories.BusinessRoleRepository;
import org.softuni.work.areas.roles.services.interfaces.BusinessRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessRoleServiceImpl implements BusinessRoleService {
    private final BusinessRoleRepository businessRoleRepository;

    @Autowired
    public BusinessRoleServiceImpl(BusinessRoleRepository businessRoleRepository) {
        this.businessRoleRepository = businessRoleRepository;
    }

    @Override
    public BusinessRole getRole(String authority) {
        return this.businessRoleRepository.findFirstByAuthority(authority);

    }

    @Override
    public BusinessRole create(BusinessRole role) {
        return this.businessRoleRepository.save(role);

    }
}
