package com.studentToolkit;

public class EventClass {
    String color,day,name;

    public EventClass() {
    }

    public EventClass(String color, String day, String name) {
        this.color = color;
        this.day = day;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
