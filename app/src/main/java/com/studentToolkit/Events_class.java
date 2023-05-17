package com.studentToolkit;

public class Events_class {
    String Id,name,logo,descripe,date,time,userid,club;

    public Events_class(String id,String userid, String name, String logo, String descripe,String date,String time, String club) {
        Id = id;
        this.name = name;
        this.userid=userid;
        this.logo = logo;
        this.descripe = descripe;
        this.date=date;
        this.time=time;
        this.club=club;
    }
    public Events_class()
    {

    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
