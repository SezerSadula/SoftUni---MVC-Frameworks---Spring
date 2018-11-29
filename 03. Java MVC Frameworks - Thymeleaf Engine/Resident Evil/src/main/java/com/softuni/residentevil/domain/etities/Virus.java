package com.softuni.residentevil.domain.etities;

import com.softuni.residentevil.domain.enums.Magnitude;
import com.softuni.residentevil.domain.enums.Mutation;
import com.softuni.residentevil.domain.etities.converters.MagnitudeAttributeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "viruses")
public final class Virus {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    // Name – Cannot be empty, should be between 3 and 10 symbols.
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    // Description – Cannot be empty, should be between 5 and 100 symbols. Represented as Text in the database
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // Side Effects – Should have a maximum of 50 symbols.
    @Column(length = 50)
    private String sideEffects;

    // Creator – Should be either Corp or corp.
    @Column(nullable = false, length = 5)
    private String creator;

    // Is Deadly – Boolean
    @Column(nullable = false)
    private Boolean isDeadly;

    // Is Curable – Boolean
    @Column(nullable = false)
    private Boolean isCurable;

    // Mutation – Cannot be null. Should hold one of the following values:
    // ZOMBIE, T_078_TYRANT, GIANT_SPIDER
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private Mutation mutation;

    // Turnover Rate – Number, between 0 and 100.
    @Column(nullable = false)
    private Integer turnoverRate;

    // THours Until Turn (to a mutation) – Number, between 1 and 12.
    @Column(nullable = false)
    private Integer hoursUntilMutation;

    // Low, Medium, High
    // Magnitude – Cannot be null. Should hold one of the following values:
    @Convert(converter = MagnitudeAttributeConverter.class)
    @Column(nullable = false, length = 6)
    private Magnitude magnitude;

    // Released On – Date, should be before the “today” date.
    @Column(nullable = false)
    private LocalDate releasedOn;

    // Capitals – A collection of Capitals.
    @ManyToMany
    @JoinTable(
            name = "viruses_capitals",
            joinColumns = {@JoinColumn(name = "virus_id")},
            inverseJoinColumns = {@JoinColumn(name = "capital_id")})
    private Set<Capital> capitals = new HashSet<>();

    @Override
    public String toString() {
        return "Virus{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sideEffects='" + sideEffects + '\'' +
                ", creator='" + creator + '\'' +
                ", isDeadly=" + isDeadly +
                ", isCurable=" + isCurable +
                ", mutation=" + mutation +
                ", turnoverRate=" + turnoverRate +
                ", hoursUntilMutation=" + hoursUntilMutation +
                ", magnitude=" + magnitude +
                ", releasedOn=" + releasedOn +
                ", capitals=" + capitals.stream().map(Capital::getName).collect(Collectors.joining(", ")) +
                '}';
    }
}
