package com.tanaseandrei.quizcourse;

/**
 * Created by asadf on 6/14/2018.
 */

public class UserPointModel {
    String id;
    String email;
    String points;
    String attempt;

    public UserPointModel(String id, String email, String points, String attempt) {
        this.id = id;
        this.email = email;
        this.points = points;
        this.attempt = attempt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }
}
