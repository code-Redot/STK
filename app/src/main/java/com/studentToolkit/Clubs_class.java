package com.studentToolkit;

public class Clubs_class {
    String Id,name,members,email,logo,descripe,user_id,approved;

    public Clubs_class(String id,String user_id, String name, String members, String email, String logo, String descripe,String approved) {
        Id = id;
        this.name = name;
        this.user_id=user_id;
        this.members = members;
        this.email = email;
        this.logo = logo;
        this.descripe = descripe;
        this.approved=approved;
    }
    public Clubs_class()
    {

    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
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

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
