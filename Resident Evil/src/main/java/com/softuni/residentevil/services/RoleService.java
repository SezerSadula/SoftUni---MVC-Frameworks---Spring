package com.softuni.residentevil.services;

import com.softuni.residentevil.domain.etities.Role;

public interface RoleService extends Creatable {

    void initRoles();

    Role getByAuthority(final String authority);
}
