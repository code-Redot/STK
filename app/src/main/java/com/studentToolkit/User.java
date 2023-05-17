package com.studentToolkit;

public class User {
    public String name;
    public String userid;
    public String imageurl;
    public String email;
    public String major;
    public String token;
    public String calendar_notifi,job_notifi,club_notifi;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String phone;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String area;


    public User(){

    }
    public User( String name,String userid, String imageurl, String email,String phone, String major, String token, String calendar_notifi,String job_notifi,String club_notifi) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.userid = userid;
        this.imageurl=imageurl;
        this.token=token;
        this.phone=phone;
        this.job_notifi=job_notifi;
        this.club_notifi=club_notifi;
        this.calendar_notifi=calendar_notifi;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCalendar_notifi() {
        return calendar_notifi;
    }

    public void setCalendar_notifi(String calendar_notifi) {
        this.calendar_notifi = calendar_notifi;
    }

    public String getJob_notifi() {
        return job_notifi;
    }

    public void setJob_notifi(String job_notifi) {
        this.job_notifi = job_notifi;
    }

    public String getClub_notifi() {
        return club_notifi;
    }

    public void setClub_notifi(String club_notifi) {
        this.club_notifi = club_notifi;
    }
}
