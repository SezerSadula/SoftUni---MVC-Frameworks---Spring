package org.softuni.kaizer.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.kaizer.domain.models.binding.WatchCreateBindingModel;
import org.softuni.kaizer.domain.models.service.WatchServiceModel;
import org.softuni.kaizer.domain.models.view.AllWatchesWatchViewModel;
import org.softuni.kaizer.domain.models.view.TopWatchesWatchViewModel;
import org.softuni.kaizer.domain.models.view.WatchDetailsViewModel;
import org.softuni.kaizer.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/watches")
public class WatchController {
    private final WatchService watchService;

    private final ModelMapper modelMapper;

    @Autowired
    public WatchController(WatchService watchService, ModelMapper modelMapper) {
        this.watchService = watchService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/top", produces = "application/json")
    public Set<TopWatchesWatchViewModel> top4Watches() {
        return this
                .watchService
                .getTop4WatchesByViews()
                .stream()
                .map(x -> this.modelMapper.map(x, TopWatchesWatchViewModel.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @GetMapping(value = "/all", produces = "application/json")
    public Set<AllWatchesWatchViewModel> allWatches() {
        return this
                .watchService
                .allWatches()
                .stream()
                .map(x -> this.modelMapper.map(x, AllWatchesWatchViewModel.class))
                .collect(Collectors.toSet());
    }

    @GetMapping(value = "/details", produces = "application/json")
    public WatchDetailsViewModel watchDetails(@RequestParam(name = "id") String id) {
        this.watchService.viewWatch(id);

        return this.modelMapper.map(
                this.watchService
                        .getWatchById(id), WatchDetailsViewModel.class);
    }

    @PostMapping("/add")
    public ResponseEntity createWatch(@ModelAttribute WatchCreateBindingModel watchCreateBindingModel) throws URISyntaxException {
        boolean result = this.watchService.createWatch(
                this.modelMapper
                        .map(watchCreateBindingModel
                                , WatchServiceModel.class));

        return ResponseEntity.created(new URI("/watches/create")).body(result);
    }
}
