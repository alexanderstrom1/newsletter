package com.example.labb.demo.controllers;

import com.example.labb.demo.models.Newsletters;
import com.example.labb.demo.repostiories.NewsletterRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsletterController {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate (NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @GetMapping(value = "/news")
    public String showAllNews (Model model){
        List<Newsletters> myNewsletters = namedParameterJdbcTemplate.query("SELECT * FROM newsletter", new NewsletterRowMapper());
        model.addAttribute("newsletters", myNewsletters);
        return "newsview";
    }
}
