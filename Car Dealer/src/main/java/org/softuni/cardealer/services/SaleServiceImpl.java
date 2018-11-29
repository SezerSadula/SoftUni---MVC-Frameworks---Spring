package org.softuni.cardealer.services;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Sale;
import org.softuni.cardealer.domain.models.view.CustomerViewModel;
import org.softuni.cardealer.domain.models.view.SaleDetailsViewModel;
import org.softuni.cardealer.domain.models.view.SaleSimpleViewModel;
import org.softuni.cardealer.domain.models.view.SaleViewModel;
import org.softuni.cardealer.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final CarService carService;

    @Autowired
    public SaleServiceImpl(final SaleRepository saleRepository,
                           final ModelMapper modelMapper,
                           final CarService carService) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.carService = carService;
    }

    @Override
    public List<SaleSimpleViewModel> getPurchasesForCustomer(final Long customerId) {
        final List<Sale> purchases = this.saleRepository
                .getPurchasesForCustomer(customerId);
        return purchases.stream()
                .map(p -> {
                    SaleSimpleViewModel model = this.modelMapper.map(p, SaleSimpleViewModel.class);
                    model.setCarBasePrice(p.getCar().calculateTotalPrice());
                    return model;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<SaleViewModel> getAllSales() {
        final List<Sale> sales = this.saleRepository.findAll();
        return getSaleViewModelsFromSales(sales);
    }

    private List<SaleViewModel> getSaleViewModelsFromSales(final List<Sale> sales) {
        final List<SaleViewModel> views = new ArrayList<>(sales.size());

        for (final Sale sale : sales) {
            final SaleViewModel model = this.modelMapper.map(sale, SaleViewModel.class);
            model.setCustomerName(sale.getCustomer().getName());
            model.setCustomerId(sale.getCustomer().getId());
            model.setCarId(sale.getCar().getId());
            model.setCarMake(sale.getCar().getMake());
            model.setCarModel(sale.getCar().getModel());
            model.setCarBasePrice(sale.getCar().calculateTotalPrice());
            model.setDiscount(sale.getDiscount() * 100.0);
            model.setTotalDiscount(this.getTotalDiscount(sale));
            model.setCarFinalPrice(model.getCarBasePrice() * this.getPriceModifier(sale));
            views.add(model);
        }

        return views;
    }

    @Override
    public SaleDetailsViewModel getSaleDetails(final Long id) {
        final Sale sale = this.saleRepository.findById(id).orElse(null);

        if (sale == null) {
            return null;
        }

        SaleDetailsViewModel model = this.modelMapper.map(sale, SaleDetailsViewModel.class);

        model.setCarBasePrice(sale.getCar().calculateTotalPrice());
        model.setCarFinalPrice(model.getCarBasePrice() * this.getPriceModifier(sale));
        model.setTotalDiscount(this.getTotalDiscount(sale));
        model.setYoungDriverDiscount(sale.getCustomer().discount() * 100.0);
        model.setDiscount(sale.getDiscount() * 100.0);
        model.setCustomerViewModel(this.modelMapper.map(sale.getCustomer(), CustomerViewModel.class));
        model.setCarWithPartsViewModel(this.carService.getCarWithParts(sale.getCar().getId()));

        return model;
    }

    @Override
    public List<SaleViewModel> getAllDiscounted() {
        final List<Sale> sales = this.saleRepository.getAllDiscounted();
        return getSaleViewModelsFromSales(sales);
    }

    @Override
    public List<SaleViewModel> getAllDiscountedByPercentage(final Integer percentage) {
        final List<Sale> sales = this.saleRepository.getAllDiscountedByPercentage(percentage * 0.01);
        return getSaleViewModelsFromSales(sales);
    }

    private double getPriceModifier(final Sale sale) {
        return 1.0 - (sale.getCustomer().discount() + sale.getDiscount());
    }

    private double getTotalDiscount(final Sale sale) {
        return (sale.getCustomer().discount() + sale.getDiscount()) * 100.0;
    }
}
