package com.example.labb.demo.controllers;

import com.example.labb.demo.models.Admin;
import com.example.labb.demo.models.Subscriber;
import com.example.labb.demo.repostiories.AdminRowMapper;
import com.example.labb.demo.repostiories.SubscriberRowMapper;
import com.example.labb.demo.service.SendEmail;
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
public class SubscriberController {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }
    @Autowired
    SendEmail sendEmail;

    @GetMapping(value = "/subscribers")
    public String showAllSubscribers (Model model){
        List<Subscriber> mySubscriber = namedParameterJdbcTemplate.query("SELECT* FROM subscriber", new SubscriberRowMapper());
        model.addAttribute("subscribers", mySubscriber);
        return "subscribers";

    }//AdminAll
    @GetMapping(value = "/addsubscriptionform")
    public String showAddSubscriptionForm() {
        return "addsubscriberform";
    }// end showAddform

    @PostMapping(value = "/addsubscription")
    public String addNewSubscriber(@ModelAttribute Subscriber subscriber){
        String insertSQLSubscriber = "INSERT INTO subscriber (email, name, company)VALUES(:email, :name, :company)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", subscriber.getEmail());
        parameters.addValue("name", subscriber.getName());
        parameters.addValue("company", subscriber.getCompany());
        namedParameterJdbcTemplate.update(insertSQLSubscriber, parameters);
        sendEmail.sendMail(subscriber.getEmail(), "Thank you for subscribing to "+ subscriber.getCompany() + "!", "Hello " + subscriber.getName()+", you have subscribed to a newsletter");
        return "redirect:/news";
    }
    @GetMapping(value = "/deletesubscription")
    public String showDeleteSubscriptionForm(){
        return "deletesubscriptionform";
    }
    @PostMapping(value = "/deletesubscriber")
    public String deleteSubscriberById(@ModelAttribute Subscriber subscriber){

        String deleteAdminSQL = "DELETE FROM subscriber WHERE email =:email";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", subscriber.getEmail());
        namedParameterJdbcTemplate.update(deleteAdminSQL, parameters);
        return "redirect:/news";
    }
}
