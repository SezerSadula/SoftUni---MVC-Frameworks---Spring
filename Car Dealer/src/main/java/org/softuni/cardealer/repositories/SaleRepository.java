package org.softuni.cardealer.repositories;

import org.softuni.cardealer.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "" +
            "SELECT s " +
            "FROM Sale AS s " +
            "WHERE s.customer.id = :customerId")
    List<Sale> getPurchasesForCustomer(@Param("customerId") final Long customerId);

    @Query(value = "" +
            "SELECT s " +
            "FROM Sale AS s " +
            "WHERE s.discount > 0 " +
            "ORDER BY s.discount")
    List<Sale> getAllDiscounted();

    @Query(value = "" +
            "SELECT s " +
            "FROM Sale AS s " +
            "WHERE s.discount = :discount")
    List<Sale> getAllDiscountedByPercentage(@Param("discount") final Double discount);
}
