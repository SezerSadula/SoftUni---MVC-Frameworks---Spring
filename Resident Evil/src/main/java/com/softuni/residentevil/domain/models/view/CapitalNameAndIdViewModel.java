package com.softuni.residentevil.domain.models.view;

import com.softuni.residentevil.domain.api.Identifiable;
import lombok.Data;

@Data
public final class CapitalNameAndIdViewModel implements Identifiable<Long> {

    private Long id;

    private String name;
}
