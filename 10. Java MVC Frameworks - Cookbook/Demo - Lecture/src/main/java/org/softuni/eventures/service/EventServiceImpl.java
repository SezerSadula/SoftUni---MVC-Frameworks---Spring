package org.softuni.eventures.service;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.entities.Event;
import org.softuni.eventures.domain.entities.User;
import org.softuni.eventures.domain.models.service.EventServiceModel;
import org.softuni.eventures.domain.models.service.MyEventsServiceModel;
import org.softuni.eventures.domain.models.service.OrderServiceModel;
import org.softuni.eventures.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final UserService userService;

    private final OrderService orderService;

    private final ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createEvent(EventServiceModel eventServiceModel) {
        Event eventEntity = this.modelMapper.map(eventServiceModel, Event.class);

        try {
            this.eventRepository.save(eventEntity);
        } catch (Exception ignored) {
            //TODO: Fix this when discover exception type.
            return false;
        }

        return true;
    }

    @Override
    public boolean orderEvent(Integer tickets, String eventId, String currentUser) {
        Event event = this
                .eventRepository
                .findById(eventId)
                .orElse(null);

        User customer =
                (User) this
                        .userService
                        .loadUserByUsername(currentUser);

        if (event == null || customer == null) {
            throw new IllegalArgumentException("Order Event or Customer cannot be null!");
        }

        OrderServiceModel orderServiceModel = new OrderServiceModel();

        orderServiceModel.setOrderedOn(LocalDateTime.now());
        orderServiceModel.setEvent(event);
        orderServiceModel.setCustomer(customer);
        orderServiceModel.setTicketsCount(tickets);

        return this.orderService.createOrder(orderServiceModel);
    }

    @Override
    public Set<EventServiceModel> getAll() {
        return this.eventRepository
                .findAll()
                .stream()
                .map(x -> this.modelMapper.map(x, EventServiceModel.class))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<MyEventsServiceModel> myEvents(String currentUser) {
        String userId = ((User) this.userService
                .loadUserByUsername(currentUser))
                .getId();

        Set<OrderServiceModel> allOrdersFromUser = this.orderService.getAllByUserId(userId);

        Set<MyEventsServiceModel> myEventsServiceModels = new HashSet<>();

        for (OrderServiceModel orderServiceModel : allOrdersFromUser) {
            MyEventsServiceModel resultModel = this
                    .modelMapper.map(orderServiceModel.getEvent(), MyEventsServiceModel.class);

            resultModel.setTickets(orderServiceModel.getTicketsCount());

            myEventsServiceModels.add(resultModel);
        }

        return myEventsServiceModels;
    }
}
