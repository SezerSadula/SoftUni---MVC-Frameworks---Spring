package org.softuni.demo.models.binding;

public class CreateStudentBindingModel {
    private String name;

    private Integer willToLive;

    public CreateStudentBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWillToLive() {
        return willToLive;
    }

    public void setWillToLive(Integer willToLive) {
        this.willToLive = willToLive;
    }
}
