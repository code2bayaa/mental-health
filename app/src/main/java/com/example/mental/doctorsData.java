package com.example.mental;

public class doctorsData {

    String name;
    String telephone;
    Integer age;
    String image;
    String email;
    String biography;
    String wAddress;
    String address;
    String certification;

    public doctorsData(String address, String name, String telephone, int age, String image, String email, String wAddress, String biography, String certification) {
        this.address = address;
        this.name = name;
        this.telephone = telephone;
        this.age = age;
        this.image = image;
        this.email = email;
        this.wAddress = wAddress;
        this.biography = biography;
        this.certification = certification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.name = address;
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

    public String getwAddress() {
        return wAddress;
    }

    public void setwAddress(String wAddress) {
        this.wAddress = wAddress;
    }

    public String getbiography() {
        return biography;
    }

    public void setbiography(String biography) {
        this.biography = biography;
    }

    public String getcertification() {
        return certification;
    }

    public void setcertification(String certification) {
        this.certification = certification;
    }


}
