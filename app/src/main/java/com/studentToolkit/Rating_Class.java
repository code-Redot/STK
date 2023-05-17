package com.studentToolkit;

public class Rating_Class {
    private String id,u_id,rate;

    public Rating_Class() {
    }

    public Rating_Class(String id, String u_id, String rate) {
        this.id = id;
        this.u_id = u_id;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
