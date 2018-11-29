package org.softuni.demo.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    private String id;

    private String name;

    private Integer willToLive;

    public Student() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "will_to_live")
    public Integer getWillToLive() {
        return willToLive;
    }

    public void setWillToLive(Integer willToLive) {
        this.willToLive = willToLive;
    }

    public String extractStudentAsString() {
        return String.format("[%s]. %s - [%d]", this.getId(), this.getName(), this.getWillToLive());
    }
}
