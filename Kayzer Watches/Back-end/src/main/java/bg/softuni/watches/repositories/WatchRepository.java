package bg.softuni.watches.repositories;

import bg.softuni.watches.domain.entities.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource
public interface WatchRepository extends JpaRepository<Watch, String> {

}

