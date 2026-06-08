package com.example.demo.dao;

import com.example.demo.model.Education;
import com.example.demo.model.EducationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EducationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Education> getEducation() {
        String sql = "SELECT * FROM education";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Education e = new Education();
            e.setEducationId(rs.getInt("EducationID"));
            e.setSchoolName(rs.getString("SchoolName"));
            e.setLocation(rs.getString("Location"));
            e.setDegree(rs.getString("Degree"));
            e.setCourse(rs.getString("Course"));
            e.setYears(rs.getString("Years"));
            return e;
        });
    }

    public List<EducationDetail> getEducationDetails() {
        String sql = "SELECT * FROM educationDetails";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EducationDetail ed = new EducationDetail();
            ed.setEducationDetailId(rs.getInt("EducationDetailId"));
            ed.setEducationId(rs.getInt("EducationID"));
            ed.setCoursework(rs.getString("Coursework"));
            ed.setDescription(rs.getString("Description"));
            return ed;
        });
    }

    // INSERT a new education record into the database
    public void insertEducation(Education e) {
        String sql = "INSERT INTO education (SchoolName, Location, Degree, Course, Years) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            e.getSchoolName(),
            e.getLocation(),
            e.getDegree(),
            e.getCourse(),
            e.getYears()
        );
    }

    public int getLastInsertedEducationId() {
        return jdbcTemplate.queryForObject("SELECT @@IDENTITY", Integer.class);
    }

    public void insertEducationDetail(int educationId, String coursework, String description) {
        String sql = "INSERT INTO educationDetails (EducationID, Coursework, Description) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, educationId, coursework, description);
    }
}