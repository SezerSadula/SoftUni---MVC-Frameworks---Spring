package com.softuni.residentevil.domain.models.view;

import com.softuni.residentevil.domain.api.Identifiable;
import com.softuni.residentevil.domain.enums.Authority;
import lombok.Data;

@Data
public final class UserViewModel implements Identifiable<String> {

    private String id;

    private String username;

    private Authority highestAuthority;
}
