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
}