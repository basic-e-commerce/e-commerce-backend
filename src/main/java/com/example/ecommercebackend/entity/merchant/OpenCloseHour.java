package com.example.ecommercebackend.entity.merchant;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public class OpenCloseHour {
    private String day;
    private String hour;
    private String endHour;

    public OpenCloseHour(String day, String  hour, String endHour) {
        this.day = day;
        this.hour = hour;
        this.endHour = endHour;
    }

    public OpenCloseHour() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }
}
