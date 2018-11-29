package org.softuni.cardealer.controllers;

import org.softuni.cardealer.domain.models.view.SaleDetailsViewModel;
import org.softuni.cardealer.domain.models.view.SaleViewModel;
import org.softuni.cardealer.services.CarService;
import org.softuni.cardealer.services.SaleService;
import org.softuni.cardealer.utils.MessageWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController extends BaseController {

    private final SaleService saleService;

    protected SalesController(final MessageWrapper messageWrapper,
                              final CarService carService,
                              final SaleService saleService) {
        super(messageWrapper, carService);
        this.saleService = saleService;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView rootGet() {
        final List<SaleViewModel> sales = this.saleService.getAllSales();
        return super.view("/sales/all", sales);
    }

    @GetMapping("/{saleId}")
    public ModelAndView getById(@PathVariable Long saleId) {
        final SaleDetailsViewModel model = this.saleService.getSaleDetails(saleId);
        return super.view("/sales/details", model);
    }

    @GetMapping("/discounted")
    public ModelAndView getDiscounted() {
        final List<SaleViewModel> sales = this.saleService.getAllDiscounted();
        return super.view("/sales/discounted", sales);
    }

    @GetMapping("/discounted/{percentage}")
    public ModelAndView getDiscountedWithPercentage(@PathVariable Integer percentage) {
        final List<SaleViewModel> sales = this.saleService.getAllDiscountedByPercentage(percentage);
        return super.view("/sales/discounted", sales);
    }
}
