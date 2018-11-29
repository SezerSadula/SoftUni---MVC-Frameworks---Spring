package org.softuni.eventures.service;

import org.softuni.eventures.domain.models.service.EventServiceModel;
import org.softuni.eventures.domain.models.service.MyEventsServiceModel;

import java.util.Set;

public interface EventService {
    boolean createEvent(EventServiceModel eventServiceModel);

    boolean orderEvent(Integer tickets, String eventId, String currentUser);

    Set<EventServiceModel> getAll();

    Set<MyEventsServiceModel> myEvents(String currentUser);
}
