package com.softuni.residentevil.domain.enums;

import com.softuni.residentevil.config.constants.Constants;

public enum Authority {
    ROOT, ADMIN, MODERATOR, USER;

    @Override
    public String toString() {
        return Constants.AUTHORITY_PREFIX + super.toString();
    }
}
