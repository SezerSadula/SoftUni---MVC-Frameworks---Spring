package org.softuni.kaizer.repository;

import org.softuni.kaizer.domain.entities.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchRepository extends JpaRepository<Watch, String> {
    List<Watch> findTop4ByOrderByViewsDesc();
}
