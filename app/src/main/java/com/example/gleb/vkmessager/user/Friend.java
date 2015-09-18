package com.example.gleb.vkmessager.user;

/**
 * Created by gleb on 27.08.15.
 */
public class Friend {
    public int id;
    public String firstName;
    public String lastName;
    public int sex;
    public int online;
    public String photo;

    public Friend(int id, String firstName, String lastName, int sex, int online, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.online = online;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getOnline() {
        return online;
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

    public void setOnline(int online) {
        this.online = online;
    }
}
