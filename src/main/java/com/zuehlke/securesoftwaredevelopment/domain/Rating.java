package com.zuehlke.securesoftwaredevelopment.domain;

public class Rating {

    private int destinationId;

    private int userId;

    private int rating;

    public Rating(int destinationId, int userId, int rating) {
        this.destinationId = destinationId;
        this.userId = userId;
        this.rating = rating;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
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
