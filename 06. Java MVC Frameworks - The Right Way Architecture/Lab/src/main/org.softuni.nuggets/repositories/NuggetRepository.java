package org.softuni.nuggets.repositories;

import org.softuni.nuggets.entities.Nugget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NuggetRepository extends JpaRepository<Nugget, String> {
}
