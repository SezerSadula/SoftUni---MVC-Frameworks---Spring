package org.softuni.cardealer.domain.models.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleViewModel {

    private Long id;

    private Double discount;

    private Long customerId;

    private Long carId;

    private String carMake;

    private String carModel;

    private String customerName;

    private Double carBasePrice;

    private Double carFinalPrice;

    private Double totalDiscount;
}
