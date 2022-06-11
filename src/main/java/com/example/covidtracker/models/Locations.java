package com.example.covidtracker.models;

public class Locations {

    private String state;
    private String country;
    private int latestReportedTotal;

    public int getDiffFromPrev() {
        return diffFromPrev;
    }

    public void setDiffFromPrev(int diffFromPrev) {
        this.diffFromPrev = diffFromPrev;
    }

    private int diffFromPrev;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    @Override
    public String toString() {
        return "Locations{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestReportedTotal='" + latestReportedTotal + '\'' +
                '}';
    }
}
