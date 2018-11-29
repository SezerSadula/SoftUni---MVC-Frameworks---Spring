package org.softuni.cardealer.domain.models.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerViewModel {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private Boolean youngDriver;
}
