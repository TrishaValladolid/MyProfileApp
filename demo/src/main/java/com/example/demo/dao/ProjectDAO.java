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

    public List<ProjectDetail> getProjectDetailsByProjectId(int projectId) {
        String sql = "SELECT * FROM projectDetails WHERE ProjectId = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProjectDetail pd = new ProjectDetail();
            pd.setProjectDetailId(rs.getInt("ProjectDetailsId"));
            pd.setDescription(rs.getString("Description"));
            pd.setProjectId(rs.getInt("ProjectId"));
            return pd;
        }, projectId);
    }

    public Project getProjectById(int id) {
        String sql = "SELECT * FROM project WHERE ProjectId = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Project p = new Project();
            p.setProjectId(rs.getInt("ProjectId"));
            p.setProjectName(rs.getString("ProjectName"));
            p.setTechnologies(rs.getString("Technologies"));
            p.setStartDate(rs.getString("StartDate"));
            p.setEndDate(rs.getString("EndDate"));
            return p;
        }, id);
    }

    public void insertProject(Project p) {
        String sql = "INSERT INTO project (ProjectName, Technologies, StartDate, EndDate) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            p.getProjectName(),
            p.getTechnologies(),
            p.getStartDate(),
            p.getEndDate()
        );
    }

    public int getLastInsertedProjectId() {
        return jdbcTemplate.queryForObject("SELECT @@IDENTITY", Integer.class);
    }

    public void insertProjectDetail(int projectId, String description) {
        String sql = "INSERT INTO projectDetails (ProjectId, Description) VALUES (?, ?)";
        jdbcTemplate.update(sql, projectId, description);
    }

    public void deleteProjectDetailsByProjectId(int projectId) {
        String sql = "DELETE FROM projectDetails WHERE ProjectId = ?";
        jdbcTemplate.update(sql, projectId);
    }

    public void updateProject(Project p) {
        String sql = "UPDATE project SET ProjectName = ?, Technologies = ?, StartDate = ?, EndDate = ? WHERE ProjectId = ?";
        jdbcTemplate.update(sql,
            p.getProjectName(),
            p.getTechnologies(),
            p.getStartDate(),
            p.getEndDate(),
            p.getProjectId()
        );
    }
}