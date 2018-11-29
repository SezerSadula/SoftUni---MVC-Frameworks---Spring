package org.softuni.cardealer.domain.models.view;

import lombok.Data;

@Data
public class SaleDetailsViewModel {

    private Long id;

    private Double carBasePrice;

    private Double carFinalPrice;

    private Double discount;

    private Double youngDriverDiscount;

    private Double totalDiscount;

    private CustomerViewModel customerViewModel;

    private CarWithPartsViewModel carWithPartsViewModel;
}
