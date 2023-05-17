package com.studentToolkit;

public class Plans {
    String ID,user_id,course,semester,year;

    public Plans() {
    }

    public Plans(String ID,String user_id, String course, String semester, String year) {
        this.ID = ID;
        this.course = course;
        this.semester = semester;
        this.year = year;
        this.user_id=user_id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
