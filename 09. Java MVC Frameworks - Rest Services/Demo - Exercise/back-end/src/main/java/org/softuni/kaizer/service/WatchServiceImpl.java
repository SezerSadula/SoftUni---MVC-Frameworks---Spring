package org.softuni.kaizer.service;

import org.modelmapper.ModelMapper;
import org.softuni.kaizer.domain.entities.Watch;
import org.softuni.kaizer.domain.models.service.WatchServiceModel;
import org.softuni.kaizer.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WatchServiceImpl implements WatchService {
    private final WatchRepository watchRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public WatchServiceImpl(WatchRepository watchRepository, ModelMapper modelMapper) {
        this.watchRepository = watchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createWatch(WatchServiceModel watchServiceModel) {
        Watch watchEntity = this.modelMapper.map(watchServiceModel, Watch.class);

        try {
            this.watchRepository.save(watchEntity);
        } catch (Exception ignored) {
            //TODO: Fix this when discover exception type.
            return false;
        }

        return true;
    }

    @Override
    public Set<WatchServiceModel> getTop4WatchesByViews() {
        return this
                .watchRepository
                .findTop4ByOrderByViewsDesc()
                .stream()
                .map(x -> this.modelMapper.map(x, WatchServiceModel.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Set<WatchServiceModel> allWatches() {
        return this
                .watchRepository
                .findAll()
                .stream()
                .map(x -> this.modelMapper.map(x, WatchServiceModel.class))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public WatchServiceModel getWatchById(String id) {
        Watch watchEntity = this.watchRepository
                .findById(id)
                .orElse(null);

        if(watchEntity == null) return null;

        return this.modelMapper.map(watchEntity, WatchServiceModel.class);
    }

    @Override
    public void viewWatch(String id) {
        Watch watch = this.watchRepository
                .findById(id)
                .orElse(null);

        if(watch == null) return;

        watch.setViews(watch.getViews() + 1);

        this.watchRepository.save(watch);
    }
}
