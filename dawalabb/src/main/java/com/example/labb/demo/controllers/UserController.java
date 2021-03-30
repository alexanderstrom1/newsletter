package com.example.labb.demo.controllers;

import com.example.labb.demo.models.Admin;
import com.example.labb.demo.models.User;
import com.example.labb.demo.repostiories.AdminRowMapper;
import com.example.labb.demo.repostiories.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.tree.RowMapper;
import java.text.AttributedString;
import java.util.List;

@Controller
public class UserController {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @GetMapping(value = "/users")
    public String showAllUsers(Model model) {
        List<User> myUser = namedParameterJdbcTemplate.query("SELECT* FROM user", new UserRowMapper());
        model.addAttribute("users", myUser);
        return "users";
    }//End get all users

}


