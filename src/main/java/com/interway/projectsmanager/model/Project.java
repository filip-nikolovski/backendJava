package com.interway.projectsmanager.model;

/**
 * Created by NikolovskiF on 01.06.2015.
 */
public class Project {

    private long id;
    private String name;
    private String active;
    private String dueTo;

    public Project() {
    }

    public Project(long id, String name, String active, String dueTo) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.dueTo = dueTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDueTo() {
        return dueTo;
    }

    public void setDueTo(String dueTo) {
        this.dueTo = dueTo;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active='" + active + '\'' +
                ", dueTo='" + dueTo + '\'' +
                '}';
    }
}
