package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean validateAdmin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM admin WHERE Username = ? AND Password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }
}