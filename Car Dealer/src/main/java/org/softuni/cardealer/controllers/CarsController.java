package org.softuni.cardealer.controllers;

import org.softuni.cardealer.domain.models.view.CarViewModel;
import org.softuni.cardealer.domain.models.view.CarWithPartsViewModel;
import org.softuni.cardealer.services.CarService;
import org.softuni.cardealer.utils.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarsController extends BaseController {

    private final CarService carService;

    @Autowired
    protected CarsController(final MessageWrapper messageWrapper,
                             final CarService carService) {
        super(messageWrapper, carService);
        this.carService = carService;
    }

    @GetMapping("/{make}")
    public ModelAndView getByMake(@PathVariable String make) {
        final List<CarViewModel> cars = this.carService.getByMake(make);
        return super.view("/cars/all-by-make", cars);
    }

    @GetMapping(value = {"/", ""})
    public ModelAndView getAll() {
        final List<CarViewModel> cars = this.carService.getAll();
        return super.view("/cars/table", cars);
    }

    @GetMapping("/{carId}/parts")
    public ModelAndView getByCarDetails(@PathVariable Long carId) {
        final CarWithPartsViewModel model = this.carService.getCarWithParts(carId);

        if (model == null) {
            return super.redirect("/cars");
        }

        return super.view("/cars/details", model);
    }
}
