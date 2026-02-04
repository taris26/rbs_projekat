package com.zuehlke.securesoftwaredevelopment.domain;

public class Destination {
    private Integer id;
    private Integer cityId;
    private String name;
    private String description;

    public Destination() {
    }

    public Destination(Integer id, Integer cityId, String name, String description) {
        this.id = id;
        this.cityId = cityId;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
