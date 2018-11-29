package org.softuni.cardealer.repositories;

import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.view.CarViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "" +
            "SELECT new org.softuni.cardealer.domain.models.view.CarViewModel(c.id, c.make, c.model, c.travelledDistance) " +
            "FROM Car AS c WHERE c.make = :make " +
            "ORDER BY c.model DESC, c.travelledDistance ASC")
    List<CarViewModel> findByMake(@Param("make") final String make);

    @Query(value = "" +
            "SELECT new org.softuni.cardealer.domain.models.view.CarViewModel(c.id, c.make, c.model, c.travelledDistance)" +
            "FROM Car AS c " +
            "ORDER BY c.make DESC, c.model DESC, c.travelledDistance ASC")
    List<CarViewModel> getAll();

    @Query(value = "" +
            "SELECT c.make " +
            "FROM Car AS c " +
            "GROUP BY c.make " +
            "ORDER BY c.make")
    List<String> getAllMakers();
}
