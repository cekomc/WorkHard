package org.softuni.work.areas.roles.services.interfaces;

import org.softuni.work.areas.roles.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String authority);

    Role create(Role role);

    List<Role> getAllRoles();

    Role getRoleId(String id);
}
