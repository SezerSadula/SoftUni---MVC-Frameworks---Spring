package org.softuni.eventures.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.models.binding.EventCreateBindingModel;
import org.softuni.eventures.domain.models.binding.EventOrderBindingModel;
import org.softuni.eventures.domain.models.service.EventServiceModel;
import org.softuni.eventures.domain.models.view.AllEventsEventViewModel;
import org.softuni.eventures.domain.models.view.MyEventsEventViewModel;
import org.softuni.eventures.service.EventService;
import org.softuni.eventures.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventController extends BaseController {
    private final EventService eventService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public EventController(EventService eventService, UserService userService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all")
    public ModelAndView allEvents(ModelAndView modelAndView) {
        Set<AllEventsEventViewModel> allEventsViewModel = this
                .eventService
                .getAll()
                .stream()
                .map(x -> this.modelMapper.map(x, AllEventsEventViewModel.class))
                .collect(Collectors.toUnmodifiableSet());

        modelAndView.addObject("allEvents", allEventsViewModel);

        return this.view("events-all", modelAndView);
    }

    @GetMapping("/my")
    public ModelAndView myEvents(Principal principal, ModelAndView modelAndView) {
        Set<MyEventsEventViewModel> myEventsViewModel = this
                .eventService
                .myEvents(principal.getName())
                .stream()
                .map(x -> this.modelMapper.map(x, MyEventsEventViewModel.class))
                .collect(Collectors.toUnmodifiableSet());

        modelAndView.addObject("myEvents", myEventsViewModel);

        return this.view("events-my", modelAndView);
    }

    @GetMapping("/create")
    public ModelAndView createEvent() {
        return this.view("events-create");
    }

    @PostMapping("/create")
    public ModelAndView createEventConfirm(@ModelAttribute EventCreateBindingModel eventCreateBindingModel) {
        boolean result = this.eventService
                .createEvent(this.modelMapper.map(eventCreateBindingModel, EventServiceModel.class));

        if (!result) {
            throw new IllegalArgumentException("asd");
        }

        return this.redirect("all");
    }

    @PostMapping("/order")
    public ModelAndView order(@ModelAttribute EventOrderBindingModel eventOrderBindingModel, Principal principal) {
        boolean result = this.eventService
                .orderEvent(eventOrderBindingModel.getTickets(), eventOrderBindingModel.getEventId(), principal.getName());

        if (!result) {
            throw new IllegalArgumentException("asd");
        }

        return this.redirect("all");
    }
}
