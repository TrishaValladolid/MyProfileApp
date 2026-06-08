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
            s.setCategory(rs.getString("Category"));
            return s;
        });
    }

    public Skill getSkillById(int id) {
        String sql = "SELECT * FROM skills WHERE SkillsId = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Skill s = new Skill();
            s.setSkillsId(rs.getInt("SkillsId"));
            s.setSkill(rs.getString("Skill"));
            s.setLevel(rs.getString("Level"));
            s.setCategory(rs.getString("Category"));
            return s;
        }, id);
    }

    public void insertSkill(Skill skill) {
        String sql = "INSERT INTO skills (Skill, Level, Category) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, skill.getSkill(), skill.getLevel(), skill.getCategory());
    }

    public void updateSkill(Skill skill) {
        String sql = "UPDATE skills SET Skill = ?, Level = ?, Category = ? WHERE SkillsId = ?";
        jdbcTemplate.update(sql, skill.getSkill(), skill.getLevel(), skill.getCategory(), skill.getSkillsId());
    }
}