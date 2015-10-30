package com.interway.projectsmanager.dao;

import com.interway.projectsmanager.model.Project;

import java.util.List;

/**
 * Created by NikolovskiF on 01.06.2015.
 */
public interface ProjectDAO {

    public void insert(Project project);

    public Project findProjectById(long projectId);

    public void update(Project project);

    public void delete(long projectId);

    public List<Project> getAll();
}
