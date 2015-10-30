package com.interway.projectsmanager.dao;

import com.interway.projectsmanager.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NikolovskiF on 27.05.2015.
 */
public class JdbcCustomerDAO implements CustomerDAO {

    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Customer customer) {

        String query = "INSERT INTO CUSTOMERS (CUSTOMER_ID, NAME, SURNAME, EMAIL, PASSWORD, ACTIVE) VALUES (?, ?, ?, ?, ?, ?)";
        String query1 = "INSERT INTO SERVICE ( customer_id, project_id, active) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        PreparedStatement ps1 = null;
        System.out.println("test");
        try {
            conn = dataSource.getConnection();
            ps1 = conn.prepareStatement(query1);
            ps = conn.prepareStatement(query);

            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getSurname());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPassword());
            ps.setString(6, customer.getActive());

            int out = ps.executeUpdate();

            if (out != 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    System.out.println("customer saved with id = " + generatedKeys.getLong(1));
                    try {
                        ps1.setLong(1, generatedKeys.getLong(1));
                        ps1.setInt(2, 1);
                        ps1.setString(3, "1");

                        int out1 = ps1.executeUpdate();

                        if (out1 != 0) {
                            System.out.println("relation added");
                        } else {
                            System.out.println("relation not added");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("customer save failed with id = " + customer.getId());
            }
        } catch (SQLException e) {
            System.out.println("customer save failed exception");
            e.printStackTrace();
        } finally {
            try {
                ps1.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Customer finByCustomerId(int customerId) {
        String query = "SELECT NAME, SURNAME FROM CUSTOMERS WHERE customer_id = ?";
        Customer cust = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                cust = new Customer();
                cust.setId(customerId);
                cust.setName(rs.getString("name"));
                cust.setSurname(rs.getString("surname"));

                System.out.println("Customer found = " + cust);
            } else {
                System.out.println("no Customer found with id = " + customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cust;
    }


    @Override
    public void update(Customer customer) {
        String query = "UPDATE CUSTOMERS SET NAME = ?, SURNAME = ? WHERE CUSTOMER_ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(3, customer.getId());
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getSurname());

            int out = ps.executeUpdate();

            if (out != 0) {
                System.out.println("customer updated with id = " + customer.getId());
            } else {
                System.out.println("customer update failed with id = " + customer.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Customer deleted with id=" + id);
            } else {
                System.out.println("No Customer found with id=" + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Customer> getAll() {
        String sql = "SELECT CUSTOMER_ID, NAME, SURNAME FROM CUSTOMERS";

        List<Customer> custList = new ArrayList<Customer>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Customer cust = new Customer();
                cust.setId(rs.getInt("CUSTOMER_ID"));
                cust.setName(rs.getString("name"));
                cust.setSurname(rs.getString("surname"));
                custList.add(cust);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return custList;
    }
}
