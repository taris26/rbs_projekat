package com.zuehlke.securesoftwaredevelopment.domain;

import java.math.BigDecimal;

public class RoomType {
    private Integer id;
    private Integer hotelId;
    private String name;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private Integer totalRooms;

    public RoomType() {
    }

    public RoomType(Integer hotelId, String name, Integer capacity, BigDecimal pricePerNight, Integer totalRooms) {
        this.hotelId = hotelId;
        this.name = name;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.totalRooms = totalRooms;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }
}
