package com.example.demo.dao;

import com.example.demo.model.Experience;
import com.example.demo.model.ExperienceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ExperienceDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Experience> getExperiences() {
        String sql = "SELECT * FROM experience";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Experience e = new Experience();
            e.setExperienceId(rs.getInt("ExperienceId"));
            e.setOrganization(rs.getString("Organization"));
            e.setLocation(rs.getString("Location"));
            e.setYears(rs.getString("Years"));
            e.setRole(rs.getString("Role"));
            return e;
        });
    }

    public List<ExperienceDetail> getExperienceDetails() {
        String sql = "SELECT * FROM experienceDetails";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ExperienceDetail ed = new ExperienceDetail();
            ed.setExperienceDetailId(rs.getInt("ExperienceDetailId"));
            ed.setDescription(rs.getString("Description"));
            ed.setExperienceId(rs.getInt("ExperienceId"));
            return ed;
        });
    }

    public List<ExperienceDetail> getExperienceDetailsByExperienceId(int experienceId) {
        String sql = "SELECT * FROM experienceDetails WHERE ExperienceId = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ExperienceDetail ed = new ExperienceDetail();
            ed.setExperienceDetailId(rs.getInt("ExperienceDetailId"));
            ed.setDescription(rs.getString("Description"));
            ed.setExperienceId(rs.getInt("ExperienceId"));
            return ed;
        }, experienceId);
    }

    public Experience getExperienceById(int id) {
        String sql = "SELECT * FROM experience WHERE ExperienceId = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Experience e = new Experience();
            e.setExperienceId(rs.getInt("ExperienceId"));
            e.setOrganization(rs.getString("Organization"));
            e.setLocation(rs.getString("Location"));
            e.setYears(rs.getString("Years"));
            e.setRole(rs.getString("Role"));
            return e;
        }, id);
    }

    public void insertExperience(Experience e) {
        String sql = "INSERT INTO experience (Organization, Location, Years, Role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            e.getOrganization(),
            e.getLocation(),
            e.getYears(),
            e.getRole()
        );
    }

    public int getLastInsertedExperienceId() {
        return jdbcTemplate.queryForObject("SELECT @@IDENTITY", Integer.class);
    }

    public void insertExperienceDetail(int experienceId, String description) {
        String sql = "INSERT INTO experienceDetails (ExperienceId, Description) VALUES (?, ?)";
        jdbcTemplate.update(sql, experienceId, description);
    }

    public void deleteExperienceDetailsByExperienceId(int experienceId) {
        String sql = "DELETE FROM experienceDetails WHERE ExperienceId = ?";
        jdbcTemplate.update(sql, experienceId);
    }

    public void updateExperience(Experience e) {
        String sql = "UPDATE experience SET Organization = ?, Location = ?, Years = ?, Role = ? WHERE ExperienceId = ?";
        jdbcTemplate.update(sql,
            e.getOrganization(),
            e.getLocation(),
            e.getYears(),
            e.getRole(),
            e.getExperienceId()
        );
    }
}