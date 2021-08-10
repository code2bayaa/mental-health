package com.example.mental;

public class patientsData {

    String name;
    String telephone;
    Integer age;
    String image;
    String email;
    String symptoms;
    float height;
    float weight;
    String uniqueRecord;
    String date;

    public patientsData(String name, String telephone, int age, String image, String email, String symptoms, float height, float weight, String uniqueRecord, String date) {
        this.name = name;
        this.telephone = telephone;
        this.age = age;
        this.image = image;
        this.email = email;
        this.symptoms = symptoms;
        this.height = height;
        this.weight = weight;
        this.uniqueRecord = uniqueRecord;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
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

    public String getUniqueRecord() {
        return uniqueRecord;
    }

    public void setUniqueRecord(String uniqueRecord) {
        this.uniqueRecord = uniqueRecord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
