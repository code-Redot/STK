package com.studentToolkit;

public class News_class {
    String Id,user_id,name,logo,descripe,club;

    public News_class(String id,String user_id, String name, String logo, String descripe,String club) {
        Id = id;
        this.name = name;
        this.user_id=user_id;
        this.logo = logo;
        this.descripe = descripe;
        this.club = club;

    }
    public News_class()
    {

    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
}
