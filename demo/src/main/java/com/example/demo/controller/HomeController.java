package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String profile(Model model) {

        // fetch first name from person
        String sql = "SELECT FirstName FROM person WHERE ID = 1;";
        String firstName = jdbcTemplate.queryForObject(sql, String.class);

        // pass it to the HTML
        model.addAttribute("firstName", firstName);

        return "profile";
    }
}