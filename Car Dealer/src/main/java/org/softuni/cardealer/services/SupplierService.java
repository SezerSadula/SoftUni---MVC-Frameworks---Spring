package org.softuni.cardealer.services;

import org.softuni.cardealer.domain.models.view.SuppliersViewModel;

import java.util.List;

public interface SupplierService {

    List<SuppliersViewModel> getSuppliersByType(final Boolean isImporter);

    List<SuppliersViewModel> getAll();
}
