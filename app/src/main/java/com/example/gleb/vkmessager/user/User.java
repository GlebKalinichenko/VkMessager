package com.example.gleb.vkmessager.user;

/**
 * Created by gleb on 24.08.15.
 */
public class User {
    public int id;
    public String firstName;
    public String lastName;
    public int sex;
    public String bDate;
    public String city;
    public String country;
    public String university;
    public String faculty;

    public User(int id, String firstName, String lastName, int sex, String bDate, String city, String country, String university, String faculty) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.bDate = bDate;
        this.city = city;
        this.country = country;
        this.university = university;
        this.faculty = faculty;
    }

    public String getUniversity() {
        return university;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSex() {
        return sex;
    }

    public String getbDate() {
        return bDate;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
