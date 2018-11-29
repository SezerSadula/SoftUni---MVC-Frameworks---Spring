package org.softuni.cardealer.controllers;

import org.softuni.cardealer.domain.models.view.SuppliersViewModel;
import org.softuni.cardealer.services.CarService;
import org.softuni.cardealer.services.SupplierService;
import org.softuni.cardealer.utils.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SuppliersController extends BaseController {

    private final SupplierService supplierService;

    @Autowired
    protected SuppliersController(final MessageWrapper messageWrapper,
                                  final CarService carService,
                                  final SupplierService supplierService) {
        super(messageWrapper, carService);
        this.supplierService = supplierService;
    }

    @GetMapping("/local")
    public ModelAndView getLocal() {
        final List<SuppliersViewModel> suppliers = this.supplierService.getSuppliersByType(false);
        return super.view("/suppliers/local", suppliers);
    }

    @GetMapping("/importers")
    public ModelAndView getImporters() {
        final List<SuppliersViewModel> suppliers = this.supplierService.getSuppliersByType(true);
        return super.view("/suppliers/importers", suppliers);
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView getAllTable() {
        final List<SuppliersViewModel> suppliers = this.supplierService.getAll();
        return super.view("/suppliers/table", suppliers);
    }
}
