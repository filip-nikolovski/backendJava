package com.interway.projectsmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by NikolovskiF on 22.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String active;


    public Customer() {
    }

    public Customer(int id, String name, String surname, String email, String password, String active) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
