package com.softuni.residentevil.domain.models.view;

import com.softuni.residentevil.domain.api.Identifiable;
import com.softuni.residentevil.domain.enums.Magnitude;
import lombok.Data;

import java.time.LocalDate;

@Data
public final class VirusIdNameMagnitudeAndDateViewModel implements Identifiable<String> {

    private String id;

    private String name;

    private Magnitude magnitude;

    private LocalDate releasedOn;
}
