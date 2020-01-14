package br.com.erico.desafioappia.models;

public class Measurement {
    private String day;
    private String medUid;

    public int getGlucose() {
        return glucose;
    }

    public void setGlucose(int glucose) {
        this.glucose = glucose;
    }

    private int glucose;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMedUid() {
        return medUid;
    }

    public void setMedUid(String medUid) {
        this.medUid = medUid;
    }

}
