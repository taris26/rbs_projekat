package com.zuehlke.securesoftwaredevelopment.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private Integer id;
    private Integer userId;
    private Integer hotelId;
    private Integer roomTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;
    private Integer guestsCount;
    private BigDecimal totalPrice;

    public Reservation() {
    }

    public Reservation(Integer userId, Integer hotelId, Integer roomTypeId,
                       LocalDate startDate, LocalDate endDate,
                       Integer roomsCount, Integer guestsCount, BigDecimal totalPrice) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomTypeId = roomTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomsCount = roomsCount;
        this.guestsCount = guestsCount;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(Integer roomsCount) {
        this.roomsCount = roomsCount;
    }

    public Integer getGuestsCount() {
        return guestsCount;
    }

    public void setGuestsCount(Integer guestsCount) {
        this.guestsCount = guestsCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
