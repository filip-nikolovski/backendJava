package com.interway.projectsmanager.controller;

import com.interway.projectsmanager.dao.ProjectDAO;
import com.interway.projectsmanager.model.Project;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by NikolovskiF on 01.06.2015.
 */

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    //Get the Spring context
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Module.xml");
    ProjectDAO projectDAO = ctx.getBean("projectDAO", ProjectDAO.class);

    //GET all projects
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Project> getAllProjects(){

        return projectDAO.getAll();
    }

    //GET Project by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project getProjectById(@PathVariable("id") long id){
        Project project = projectDAO.findProjectById(id);
        return  project;
    }

    //insert new project
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Project> create(@RequestBody Project project){

        ResponseEntity<Project> response = null;

        try {
            projectDAO.insert(project);
            response = new ResponseEntity<Project>(project, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<Project>(project, HttpStatus.BAD_REQUEST);
            e.printStackTrace();
        }

        return response;
    }






}
