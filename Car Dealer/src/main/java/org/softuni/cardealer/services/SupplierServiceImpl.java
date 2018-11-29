package org.softuni.cardealer.services;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.view.SuppliersViewModel;
import org.softuni.cardealer.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(final SupplierRepository supplierRepository,
                               final ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    private List<SuppliersViewModel> collectionToViewModel(final List<Supplier> suppliers) {
        return suppliers.stream()
                .map(supplier -> {
                    final SuppliersViewModel model = this.modelMapper.map(supplier, SuppliersViewModel.class);
                    model.setSuppliedPartsCount(supplier.getParts().size());
                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SuppliersViewModel> getSuppliersByType(final Boolean isImporter) {
        final List<Supplier> suppliers = this.supplierRepository.getSuppliersByType(isImporter);

        return collectionToViewModel(suppliers);
    }

    @Override
    public List<SuppliersViewModel> getAll() {
        final List<Supplier> suppliers = this.supplierRepository.findAll();

        return collectionToViewModel(suppliers);
    }
}
