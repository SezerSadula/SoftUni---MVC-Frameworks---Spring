package org.softuni.cardealer.services;

import org.softuni.cardealer.domain.models.view.SaleDetailsViewModel;
import org.softuni.cardealer.domain.models.view.SaleSimpleViewModel;
import org.softuni.cardealer.domain.models.view.SaleViewModel;

import java.util.List;

public interface SaleService {

    List<SaleSimpleViewModel> getPurchasesForCustomer(final Long customerId);

    List<SaleViewModel> getAllSales();

    SaleDetailsViewModel getSaleDetails(final Long id);

    List<SaleViewModel> getAllDiscounted();

    List<SaleViewModel> getAllDiscountedByPercentage(final Integer percentage);
}
