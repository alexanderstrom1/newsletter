package com.example.labb.demo.controllers;

import com.example.labb.demo.models.Admin;
import com.example.labb.demo.models.Subscriber;
import com.example.labb.demo.repostiories.AdminRowMapper;
import com.example.labb.demo.repostiories.SubscriberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate (NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;

    }//End auto
    @GetMapping(value = "/admins")
    public String showAllAdmins (Model model){
        List<Admin> myAdmin = namedParameterJdbcTemplate.query("SELECT* FROM admin", new AdminRowMapper());
        model.addAttribute("admins", myAdmin);

        List<Subscriber> allSubscribers = namedParameterJdbcTemplate.query("SELECT * FROM subscriber", new SubscriberRowMapper());
        model.addAttribute("subscribers", allSubscribers);
        return "admin";
    }//AdminAll
    @GetMapping(value="/admineditform/{id}")
    public String showEditFormByAdminId(@PathVariable String id, Model model){
        String selectSQLAdminById = "SELECT * FROM admin WHERE id =:myid";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("myid", id);

        Admin admin = (Admin) namedParameterJdbcTemplate.queryForObject(selectSQLAdminById, parameters, new AdminRowMapper());

        model.addAttribute("admin", admin);
        return "editAdminForm";

    }//End admineditform/{id}
    @PostMapping(value = "/updateadmin")
    public String updateAdmin(@ModelAttribute Admin admin){

        String updateSQLAdmin = "UPDATE admin SET name=:name, email=:email WHERE id=:myid";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("myid", admin.getId());
        parameters.addValue("name", admin.getName());
        parameters.addValue("email", admin.getEmail());
        namedParameterJdbcTemplate.update(updateSQLAdmin, parameters);
        return "redirect:/admins";
    }
    @GetMapping(value = "/addform")
    public String showAddForm() {
        return "addadminformview";
    }// end showAddform

    @PostMapping(value = "/admin")
    public String addNewAdmin(@ModelAttribute Admin admin){
        String insertSQLAdmin = "INSERT INTO admin (name, email)VALUES(:name, :email)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", admin.getName());
        parameters.addValue("email", admin.getEmail());
        namedParameterJdbcTemplate.update(insertSQLAdmin, parameters);
        return "redirect:/admins";
    }
    @GetMapping(value = "/deleteadmin/{id}")
    public String deleteAdminById(@PathVariable String id){

        String deleteAdminSQL = "DELETE FROM admin WHERE id =:myid";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("myid", id);
        namedParameterJdbcTemplate.update(deleteAdminSQL, parameters);
        return "redirect:/admins";
    }



}//End class
