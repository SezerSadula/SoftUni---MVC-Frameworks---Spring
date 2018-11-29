package org.softuni.cardealer.services;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.view.CarViewModel;
import org.softuni.cardealer.domain.models.view.CarWithPartsViewModel;
import org.softuni.cardealer.domain.models.view.PartViewModel;
import org.softuni.cardealer.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(final CarRepository carRepository,
                          final ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarViewModel> getByMake(final String make) {
        return this.carRepository.findByMake(make);
    }

    @Override
    public List<CarViewModel> getAll() {
        return this.carRepository.getAll();
    }

    @Override
    public List<String> getMakers() {
        return this.carRepository.getAllMakers();
    }

    @Override
    public CarWithPartsViewModel getCarWithParts(final Long carId) {
        final Car car = this.carRepository
                .findById(carId)
                .orElse(null);

        if (car == null) {
            return null;
        }

        final CarWithPartsViewModel model = this.modelMapper.map(car, CarWithPartsViewModel.class);
        model.setParts(car.getParts()
                .stream()
                .map(part -> this.modelMapper.map(part, PartViewModel.class))
                .collect(Collectors.toUnmodifiableList()));

        return model;
    }
}
