package com.interway.projectsmanager.dao;

import com.interway.projectsmanager.model.Customer;
import com.interway.projectsmanager.model.Project;
import com.interway.projectsmanager.model.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NikolovskiF on 02.06.2015.
 */
public class JdbcServiceDAO implements ServiceDAO {

    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ResponseEntity<Service> insert(Service service) {

        String query1 = "INSERT INTO SERVICE ( service_id, customer_id, project_id, active) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps1 = null;
        ResponseEntity<Service> response = null;

        try {
            conn = dataSource.getConnection();
            ps1 = conn.prepareStatement(query1);

            ps1.setLong(1, service.getId());
            ps1.setInt(2, service.getCustomer().iterator().next().getId());
            ps1.setLong(3, service.getProject().iterator().next().getId());
            ps1.setString(4, service.getActive());

            int out1 = ps1.executeUpdate();

            if (out1 != 0) {
                System.out.println("relation added");
                response = new ResponseEntity<Service>(service, HttpStatus.OK);
            } else {
                System.out.println("relation not added");
            }
        } catch (SQLException e) {
            System.out.println("ERROR!");
             response = new ResponseEntity<Service>(service, HttpStatus.BAD_REQUEST);

            e.printStackTrace();
        } finally {
            try {
                ps1.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public ResponseEntity<List<Project>> findProjectsByCusId(int customerId) {

        String sql = "SELECT DISTINCT P.* FROM CUSTOMERS C, PROJECTS P, SERVICE S  WHERE C.customer_id = ? " +
                "AND C.customer_id = S.customer_id";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Project> projects = new ArrayList<Project>();
        ResponseEntity<List<Project>> response;

//        ResponseEntity<List<Project>> response = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, customerId);
            rs = ps.executeQuery();

            while(rs.next()){
               Project project = new Project();
                project.setId(rs.getInt("project_id"));
                project.setName(rs.getString("name"));
                project.setActive(rs.getString("active"));
                project.setDueTo(rs.getString("dueto"));

                projects.add(project);
            }

            response = new ResponseEntity<List<Project>>(projects, HttpStatus.OK);

        }catch (SQLException e){
            response =  new ResponseEntity<List<Project>>(projects, HttpStatus.BAD_REQUEST);
            e.printStackTrace();
        }
//        response = new ResponseEntity<projects, HttpStatus.Ok>()

//        return projects;
        return response;
    }

    @Override
    public List<Customer> findCustomersByProjId(long projectId) {

        String sql = "SELECT DISTINCT C.* FROM CUSTOMERS C, PROJECTS P, SERVICE S  WHERE P.project_id = ? " +
                "AND P.project_id = S.project_id";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Customer> customers = new ArrayList<Customer>();

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, projectId);
            rs = ps.executeQuery();

            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setSurname(rs.getString("surname"));
                customer.setEmail(rs.getString("email"));
                customer.setActive(rs.getString("active"));

                customers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public void delete(long serviceId) {
        String sql = "DELETE FROM SERVICE WHERE SERVICE_ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResponseEntity<Service> response = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, serviceId);
            int out = ps.executeUpdate();

            if(out != 0){
                System.out.println("Delete service successfully, id = " +serviceId);
            }else{
                System.out.println("Delete service unsuccessfully, id = " +serviceId);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }finally {
            try {
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Service> getAll() {
        return null;
    }
}
