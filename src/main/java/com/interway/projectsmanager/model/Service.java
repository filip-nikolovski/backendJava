package com.interway.projectsmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;

/**
 * Created by NikolovskiF on 02.06.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Service {

    private long id;
    private String active;
    private Collection<Customer> customer;
    private Collection<Project> project;

    public Service() {
    }

    public Service(long id, String active, Collection<Customer> customer, Collection<Project> project) {
        this.id = id;
        this.active = active;
        this.customer = customer;
        this.project = project;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Collection<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Collection<Customer> customer) {
        this.customer = customer;
    }

    public Collection<Project> getProject() {
        return project;
    }

    public void setProject(Collection<Project> project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", active='" + active + '\'' +
                ", customer=" + customer +
                ", project=" + project +
                '}';
    }
}
