package bg.softuni.watches.controllers;

import bg.softuni.watches.domain.entities.Watch;
import bg.softuni.watches.repositories.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@BasePathAwareController
public class WatchesController {

    private final WatchRepository watchRepository;

    @Autowired
    public WatchesController(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    @GetMapping(path = "/watches/{id}", produces = "application/hal+json")
    public ResponseEntity watchDetails(@PathVariable String id) {
        return getWatchView(id);
    }

    private ResponseEntity getWatchView(String id) {
        Watch watch = this.watchRepository
                .findById(id)
                .orElse(null);

        if (watch == null) {
            return ResponseEntity.notFound().build();
        }

        watch.setViews(watch.getViews() + 1);

        this.watchRepository.save(watch);

        return ResponseEntity.ok(watch);
    }
}
