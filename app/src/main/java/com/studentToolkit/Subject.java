package com.studentToolkit;

public class Subject {
    String id,name,code,hours,branch,year,semester;

    public Subject()
    {

    }
    public Subject(String id, String name, String code, String hours, String branch,String year,String semester) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.hours = hours;
        this.branch = branch;
        this.year=year;
        this.semester=semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
