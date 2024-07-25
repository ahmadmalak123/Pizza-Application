package com.example.pizzapp;


import java.text.SimpleDateFormat;
import java.util.Date;
// Review.java
public class Review {
    private String userEmail;
    private Date timestamp; // Use Date for timestamp
    private String reviewText;
    private float rating; // Changed to float for decimal rating


    public Review() {
    }

    public Review(String userEmail, Date timestamp, String reviewText, float rating) {
        this.userEmail = userEmail;
        this.timestamp = timestamp;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    public String getFormattedTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        return dateFormat.format(timestamp);
    }
    // Add constructors and getter methods
}
