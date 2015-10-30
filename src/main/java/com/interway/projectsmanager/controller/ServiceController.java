package com.interway.projectsmanager.controller;

import com.interway.projectsmanager.dao.ServiceDAO;
import com.interway.projectsmanager.model.Customer;
import com.interway.projectsmanager.model.Project;
import com.interway.projectsmanager.model.Service;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by NikolovskiF on 02.06.2015.
 */
@RestController
@RequestMapping("/api/service")
public class ServiceController {

    //Get the Spring context
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Module.xml");
    ServiceDAO serviceDAO = ctx.getBean("serviceDAO", ServiceDAO.class);

    //Insert New Relation
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Service> createService(@RequestBody Service service){

        return serviceDAO.insert(service);
    }

    //Delete relation (permanent remove product from user)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteRelation(@PathVariable("id") long id){

        serviceDAO.delete(id);
    }

    //get all customers for project
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public List<Customer> getCustomersByProduct(@PathVariable("id") long id){

        return serviceDAO.findCustomersByProjId(id);
    }

    //get all projects for customer
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getProjectsByProduct(@PathVariable("id") int id){

        return serviceDAO.findProjectsByCusId(id);
    }

}
