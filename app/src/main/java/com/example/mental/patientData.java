package com.example.mental;

public class patientData {

    String doctorName;
    String doctorImage;
    String medication;
    String analysis;
    String symptoms;
    String sickness;
    float height;
    float weight;
    String date;

    public patientData(String sickness, String symptoms, String doctorImage, String doctorName, String medication, String analysis, float height, float weight, String date) {
        this.doctorName = doctorName;
        this.medication = medication;
        this.analysis = analysis;
        this.height = height;
        this.weight = weight;
        this.date = date;
        this.sickness = sickness;
        this.date = date;
        this.symptoms = symptoms;
        this.doctorImage = doctorImage;
    }


    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
