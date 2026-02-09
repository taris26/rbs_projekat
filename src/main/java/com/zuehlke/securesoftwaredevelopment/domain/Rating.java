package com.zuehlke.securesoftwaredevelopment.domain;

public class Rating {

    private int hotelId;

    private int userId;

    private int rating;

    public Rating(int destinationId, int userId, int rating) {
        this.hotelId = destinationId;
        this.userId = userId;
        this.rating = rating;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
