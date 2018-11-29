package org.softuni.kaizer.service;

import org.softuni.kaizer.domain.models.service.WatchServiceModel;

import java.util.Set;

public interface WatchService {
    boolean createWatch(WatchServiceModel watchServiceModel);

    Set<WatchServiceModel> getTop4WatchesByViews();

    Set<WatchServiceModel> allWatches();

    WatchServiceModel getWatchById(String id);

    void viewWatch(String id);
}
