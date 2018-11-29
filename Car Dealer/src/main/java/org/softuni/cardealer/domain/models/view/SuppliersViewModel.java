package org.softuni.cardealer.domain.models.view;

import lombok.Data;

@Data
public class SuppliersViewModel {

    private Long id;

    private String name;

    private Boolean importer;

    private Integer suppliedPartsCount;
}
