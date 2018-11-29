package org.softuni.cardealer.repositories;

import org.softuni.cardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = "" +
            "SELECT s " +
            "FROM Supplier AS s " +
            "WHERE s.importer = :isImporter " +
            "ORDER BY s.id ASC")
    List<Supplier> getSuppliersByType(@Param("isImporter") final Boolean isImporter);
}
