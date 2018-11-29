package org.softuni.cardealer.repositories;

import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.view.CustomerViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "" +
            "SELECT new org.softuni.cardealer.domain.models.view.CustomerViewModel(c.id, c.name, c.birthDate, c.youngDriver) " +
            "FROM Customer AS c " +
            "ORDER BY c.birthDate DESC, c.youngDriver ASC")
    List<CustomerViewModel> getByBirthDateDesc();

    @Query(value = "" +
            "SELECT new org.softuni.cardealer.domain.models.view.CustomerViewModel(c.id, c.name, c.birthDate, c.youngDriver) " +
            "FROM Customer AS c " +
            "ORDER BY c.birthDate ASC, c.youngDriver ASC")
    List<CustomerViewModel> getByBirthDateAsc();

}
