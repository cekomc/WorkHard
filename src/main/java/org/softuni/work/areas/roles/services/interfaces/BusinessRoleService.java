package org.softuni.work.areas.roles.services.interfaces;

import org.softuni.work.areas.roles.entities.BusinessRole;

public interface BusinessRoleService{
    BusinessRole getRole(String authority);

    BusinessRole create(BusinessRole role);
}
