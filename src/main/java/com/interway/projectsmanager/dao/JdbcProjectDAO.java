package com.interway.projectsmanager.dao;

import com.interway.projectsmanager.model.Project;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NikolovskiF on 01.06.2015.
 */

public class JdbcProjectDAO implements ProjectDAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Project project) {
        String sql = "INSERT INTO PROJECTS (PROJECT_ID, NAME, ACTIVE, DUETO) VALUES(?, ?, ? ,?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, project.getId());
            ps.setString(2, project.getName());
            ps.setString(3, project.getActive());
            ps.setString(4, project.getDueTo());
            int out = ps.executeUpdate();

            if(out != 0){
                System.out.println("Project saved with id = "+ project.getId());
            }else{
                System.out.println("Projects save failed with id = "+project.getId());

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public Project findProjectById(long projectId) {
        String sql = "SELECT * FROM PROJECTS WHERE PROJECT_ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Project project = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, projectId);
            rs = ps.executeQuery();

            if(rs.next()){
                project = new Project();
                project.setId(projectId);
                project.setName(rs.getString("name"));
                project.setActive(rs.getString("active"));
                project.setDueTo(rs.getString("dueto"));

                System.out.println("Project found = " + project);
            }else{
                System.out.println("no project found with id = "+projectId);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return project;
    }

    @Override
    public void update(Project project) {
        String sql = "UPDATE PROJECTS SET NAME = ?, ACTIVE = ?, DUETO = ? WHERE PROJECT_ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, project.getName());
            ps.setString(2, project.getActive());
            ps.setString(3, project.getDueTo());
            ps.setLong(4, project.getId());
            int out = ps.executeUpdate();

            if(out != 0){
                System.out.println("Project updated with id = "+ project.getId());
            }else{
                System.out.println("Update project with id "+project.getId()+" failed");
            }
        } catch (SQLException e) {
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
    public void delete(long projectId) {
        String sql = "DELETE FROM PROJECTS WHERE PROJECT_ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
         try {
             conn = dataSource.getConnection();
             ps = conn.prepareStatement(sql);
             ps.setLong(1, projectId);
             int out = ps.executeUpdate();

             if(out != 0){
                 System.out.println("Delete project successfully, id = " +projectId);
             }else{
                 System.out.println("Delete project unsuccessfully, id = " +projectId);
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
    public List<Project> getAll() {

        String sql = "SELECT * FROM PROJECTS";
        List<Project> projectsList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                Project project = new Project();
                project.setId(rs.getLong("project_id"));
                project.setName(rs.getString("name"));
                project.setActive(rs.getString("active"));
                project.setDueTo(rs.getString("dueto"));

                projectsList.add(project);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return projectsList;
    }
}
