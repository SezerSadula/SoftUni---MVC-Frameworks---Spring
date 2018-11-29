package org.softuni.cardealer.services;

import org.softuni.cardealer.domain.models.view.CarViewModel;
import org.softuni.cardealer.domain.models.view.CarWithPartsViewModel;

import java.util.List;

public interface CarService {

    List<CarViewModel> getByMake(final String make);

    List<CarViewModel> getAll();

    List<String> getMakers();

    CarWithPartsViewModel getCarWithParts(final Long carId);
}
