package com.example.willthespoderman.babynurtures.models;

/**
 * Created by Dave on 11/14/2015.
 */
public class Notification {
    private String message;
    private String createdAt;
    private String image;

    public Notification() {}

    public Notification(String message, String createdAt, String image) {
        this.image = image;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
