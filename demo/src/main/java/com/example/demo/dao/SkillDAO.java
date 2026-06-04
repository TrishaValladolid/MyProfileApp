package com.example.demo.dao;

import com.example.demo.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SkillDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Skill> getSkills() {
        String sql = "SELECT * FROM skills";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Skill s = new Skill();
            s.setSkillsId(rs.getInt("SkillsId"));
            s.setSkill(rs.getString("Skill"));
            s.setLevel(rs.getString("Level"));
            return s;
        });
    }
}