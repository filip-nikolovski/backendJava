package com.interway.projectsmanager.dao;

import com.interway.projectsmanager.model.Customer;
import com.interway.projectsmanager.model.Project;
import com.interway.projectsmanager.model.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by NikolovskiF on 02.06.2015.
 */
public interface ServiceDAO {

    ResponseEntity<Service> insert(Service service);

    ResponseEntity<List<Project>> findProjectsByCusId(int customerId);

    List<Customer> findCustomersByProjId(long projectId);

//    public void update(Project project);

    void delete(long projectId);

    List<Service> getAll();
}
