package org.softuni.cardealer.controllers;

import org.softuni.cardealer.domain.models.view.CustomerDetailsViewModel;
import org.softuni.cardealer.domain.models.view.CustomerViewModel;
import org.softuni.cardealer.services.CarService;
import org.softuni.cardealer.services.CustomerService;
import org.softuni.cardealer.utils.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customers")
public class CustomersController extends BaseController {

    private final CustomerService customerService;

    @Autowired
    protected CustomersController(final MessageWrapper messageWrapper,
                                  final CarService carService,
                                  final CustomerService customerService) {
        super(messageWrapper, carService);
        this.customerService = customerService;
    }

    @GetMapping("/all/ascending")
    public ModelAndView getAllAscending() {
        final Iterable<CustomerViewModel> byBirthDateAscending =
                this.customerService.getByBirthDate(false);
        return super.view("/customers/all", byBirthDateAscending);
    }

    @GetMapping("/all/descending")
    public ModelAndView getAllDescending() {
        final Iterable<CustomerViewModel> byBirthDateDescending =
                this.customerService.getByBirthDate(true);
        return super.view("/customers/all", byBirthDateDescending);
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView getAllTable() {
        final Iterable<CustomerViewModel> byBirthDateDescending =
                this.customerService.getByBirthDate(true);
        return super.view("/customers/table", byBirthDateDescending);
    }

    @GetMapping("/{customerId}")
    public ModelAndView getByMake(@PathVariable Long customerId) {
        final CustomerDetailsViewModel model = this.customerService.getDetailsById(customerId);
        return super.view("/customers/details", model);
    }
}
