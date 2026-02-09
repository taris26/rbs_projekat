package com.zuehlke.securesoftwaredevelopment.domain;

public class Hotel {

    private Integer id;
    private Integer cityId;
    private String cityName;
    private String name;
    private String description;
    private String address;

    public Hotel() {
    }

    public Hotel(Integer cityId, String name, String description, String address) {
        this.cityId = cityId;
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public Hotel(Integer id, Integer cityId, String name, String description, String address) {
        this.id = id;
        this.cityId = cityId;
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public Hotel(Integer id, Integer cityId, String name, String cityName, String description, String address) {
        this.id = id;
        this.cityId = cityId;
        this.cityName = cityName;
        this.name = name;
        this.description = description;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
