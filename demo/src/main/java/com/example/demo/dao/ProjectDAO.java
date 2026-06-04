package com.example.demo.dao;

import com.example.demo.model.Project;
import com.example.demo.model.ProjectDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProjectDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Project> getProjects() {
        String sql = "SELECT * FROM project";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Project p = new Project();
            p.setProjectId(rs.getInt("ProjectId"));
            p.setProjectName(rs.getString("ProjectName"));
            p.setTechnologies(rs.getString("Technologies"));
            p.setStartDate(rs.getString("StartDate"));
            p.setEndDate(rs.getString("EndDate"));
            return p;
        });
    }

    public List<ProjectDetail> getProjectDetails() {
        String sql = "SELECT * FROM projectDetails";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProjectDetail pd = new ProjectDetail();
            pd.setProjectDetailId(rs.getInt("ProjectDetailsId"));
            pd.setDescription(rs.getString("Description"));
            pd.setProjectId(rs.getInt("ProjectId"));
            return pd;
        });
    }
}