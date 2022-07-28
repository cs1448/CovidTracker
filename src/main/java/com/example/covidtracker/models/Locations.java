package com.example.covidtracker.models;

import java.util.Date;

public class Locations {

    private String state;
    private String country;
    private int latestReportedTotal;
    private String date;

    public int getDiffFromPrev() {
        return diffFromPrev;
    }

    public void setDiffFromPrev(int diffFromPrev) {
        this.diffFromPrev = diffFromPrev;
    }

    private int diffFromPrev;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestReportedTotal() {
        return latestReportedTotal;
    }

    public void setLatestReportedTotal(int latestReportedTotal) {
        this.latestReportedTotal = latestReportedTotal;
    }
    public void setDate(String date){this.date = date;}

    @Override
    public String toString() {
        return "Locations{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestReportedTotal='" + latestReportedTotal + '\'' +
                '}';
    }
}
