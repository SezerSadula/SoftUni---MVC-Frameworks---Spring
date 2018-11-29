package com.softuni.residentevil.controllers;

import com.softuni.residentevil.domain.enums.Magnitude;
import com.softuni.residentevil.domain.enums.Mutation;
import com.softuni.residentevil.domain.models.binding.VirusAddEditBindingModel;
import com.softuni.residentevil.domain.models.view.VirusIdNameMagnitudeAndDateViewModel;
import com.softuni.residentevil.services.CapitalService;
import com.softuni.residentevil.services.VirusService;
import com.softuni.residentevil.utils.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private final VirusService virusService;
    private final CapitalService capitalsService;

    @Autowired
    public VirusController(
            final MessageWrapper messageWrapper,
            final VirusService virusService,
            final CapitalService capitalsService) {
        super(messageWrapper);
        this.virusService = virusService;
        this.capitalsService = capitalsService;
    }

    @GetMapping(value = {"", "/"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView rootGet() {
        final List<VirusIdNameMagnitudeAndDateViewModel> simpleView =
                this.virusService.getSimpleView();
        return super.view("/viruses/all", simpleView);
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView addGet() {
        final VirusAddEditBindingModel virusDto = this.loadDataToViewModel(new VirusAddEditBindingModel());
        return super.view("/viruses/add", virusDto);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView addPost(@Valid @ModelAttribute("viewModel") final VirusAddEditBindingModel virusAddEditBindingModel,
                                final BindingResult bindingResult) {

        this.loadDataToViewModel(virusAddEditBindingModel);
        if (bindingResult.hasErrors()) {
            return super.view("/viruses/add", virusAddEditBindingModel);
        }

        if (!this.virusService.create(virusAddEditBindingModel)) {
            // TODO - proper error handling
            return super.view("/viruses/add", virusAddEditBindingModel);
        }

        return super.redirect("/viruses");
    }

    @GetMapping("/edit/{virusId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView editGet(@PathVariable String virusId) {
        final VirusAddEditBindingModel virusAddEditBindingModel =
                this.loadDataToViewModel(this.virusService.getById(virusId), virusId);

        if (virusAddEditBindingModel == null) {
            return super.redirect("/viruses");
        }

        return super.view("/viruses/edit", virusAddEditBindingModel);
    }

    @PostMapping("/edit/{virusId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView editPost(@PathVariable String virusId,
                                 @Valid @ModelAttribute("viewModel") final VirusAddEditBindingModel virusAddEditBindingModel,
                                 final BindingResult bindingResult) {
        if (virusAddEditBindingModel == null) {
            return super.redirect("/viruses");
        }

        this.loadDataToViewModel(virusAddEditBindingModel, virusId);

        if (bindingResult.hasErrors() || !this.virusService.update(virusAddEditBindingModel)) {
            return super.view("/viruses/edit", virusAddEditBindingModel);
        }

        return super.redirect("/viruses");
    }

    @GetMapping("/delete/{virusId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView deleteGet(@PathVariable String virusId) {
        this.virusService.removeById(virusId);
        return super.redirect("/viruses");
    }

    private VirusAddEditBindingModel loadDataToViewModel(final VirusAddEditBindingModel virusAddEditBindingModel) {
        return this.loadDataToViewModel(virusAddEditBindingModel, null);
    }

    private VirusAddEditBindingModel loadDataToViewModel(final VirusAddEditBindingModel virusAddEditBindingModel,
                                                         final String virusId) {
        if (virusAddEditBindingModel == null) {
            return null;
        }

        virusAddEditBindingModel.setAllCapitals(this.capitalsService.getSimpleView());

        virusAddEditBindingModel.setAllMutations(
                Stream.of(Mutation.values())
                        .map(Enum::name)
                        .collect(Collectors.toUnmodifiableList()));

        virusAddEditBindingModel.setAllMagnitudes(
                Stream.of(Magnitude.values())
                        .map(Magnitude::getNormalizedName)
                        .collect(Collectors.toUnmodifiableList()));

        virusAddEditBindingModel.setId(virusId);

        return virusAddEditBindingModel;
    }
}
