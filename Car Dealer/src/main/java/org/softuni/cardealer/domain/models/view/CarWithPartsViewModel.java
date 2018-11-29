package org.softuni.cardealer.domain.models.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"parts"})
public class CarWithPartsViewModel {

    private Long id;

    private String make;

    private String model;

    private Long travelledDistance;

    private List<PartViewModel> parts = new ArrayList<>();
}
