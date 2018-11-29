package org.softuni.cardealer.services;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.view.CustomerDetailsViewModel;
import org.softuni.cardealer.domain.models.view.CustomerViewModel;
import org.softuni.cardealer.domain.models.view.SaleSimpleViewModel;
import org.softuni.cardealer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SaleService saleService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(final CustomerRepository customerRepository,
                               final SaleService saleService,
                               final ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.saleService = saleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerViewModel> getByBirthDate(final Boolean isOrderDescending) {
        return isOrderDescending
                ? this.customerRepository.getByBirthDateDesc()
                : this.customerRepository.getByBirthDateAsc();
    }

    @Override
    public CustomerDetailsViewModel getDetailsById(final Long customerId) {
        final Customer customer = this.customerRepository
                .findById(customerId)
                .orElse(null);

        if (customer == null) {
            return null;
        }

        final CustomerDetailsViewModel model = this.modelMapper.map(customer, CustomerDetailsViewModel.class);

        final List<SaleSimpleViewModel> purchases = this.saleService.getPurchasesForCustomer(customerId);


        model.setCarsBought(purchases.size());
        model.setMoneySpent(purchases.stream()
                .map(p -> p.getCarBasePrice() * (1.0 - (p.getDiscount() + customer.discount())))
                .reduce(0.0d, Double::sum));

        return model;
    }
}
