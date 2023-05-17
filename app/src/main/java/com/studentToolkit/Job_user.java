package com.studentToolkit;

public class Job_user {
    private String id, j_id,u_id,cv;

    public Job_user() {
    }

    public Job_user(String id, String j_id, String u_id, String cv) {
        this.id = id;
        this.j_id = j_id;
        this.u_id = u_id;
        this.cv = cv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJ_id() {
        return j_id;
    }

    public void setJ_id(String j_id) {
        this.j_id = j_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
