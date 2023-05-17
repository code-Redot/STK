package com.studentToolkit;

public class Club_User {
    private String id, c_id,u_id;

    public Club_User() {
    }

    public Club_User(String id,String c_id, String u_id) {
        this.c_id = c_id;
        this.u_id = u_id;
        this.id=id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
