package com.softuni.residentevil.domain.etities;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "capitals")
public final class Capital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @ManyToMany(mappedBy = "capitals")
    private Set<Virus> viruses = new HashSet<>();

    @Override
    public String toString() {
        return "Capital{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", viruses=" + viruses.stream().map(Virus::getName).collect(Collectors.joining(", ")) +
                '}';
    }
}
