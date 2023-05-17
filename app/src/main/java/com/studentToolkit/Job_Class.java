package com.studentToolkit;

public class Job_Class {
    String Id,user_id,name,logo,descripe,payment;

    public Job_Class() {

    }

    public Job_Class(String id, String user_id, String name, String logo, String descripe, String payment) {
        Id = id;
        this.user_id = user_id;
        this.name = name;
        this.logo = logo;
        this.descripe = descripe;
        this.payment = payment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
