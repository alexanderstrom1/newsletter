package com.example.labb.demo.controllers;

import com.example.labb.demo.models.Admin;
import com.example.labb.demo.models.Newsletters;
import com.example.labb.demo.models.Subscriber;
import com.example.labb.demo.repostiories.AdminRowMapper;
import com.example.labb.demo.repostiories.NewsletterRowMapper;
import com.example.labb.demo.repostiories.SubscriberRowMapper;
import com.example.labb.demo.service.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class NewsletterController {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate (NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Autowired
    SendEmail sendEmail;

    @GetMapping(value = "/news")
    public String showAllNews (Model model, Principal loggedInUser, Authentication authentication){
        List<Newsletters> myNewsletters = namedParameterJdbcTemplate.query("SELECT * FROM newsletter", new NewsletterRowMapper());
        model.addAttribute("newsletters", myNewsletters);
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        model.addAttribute("nameprincipal", loggedInUser);
        model.addAttribute("nameauth", authentication);
        model.addAttribute("username", attributes.get("login"));
        model.addAttribute("avatar_url", attributes.get("avatar_url"));
        model.addAttribute("html_url", attributes.get("html_url"));
        List<Subscriber> allSubscribers = namedParameterJdbcTemplate.query("SELECT * FROM subscriber", new SubscriberRowMapper());
        model.addAttribute("subscribers", allSubscribers);

        return "newsview";
    }
    @GetMapping(value = "/addnews")
    public String showAddNewsForm() {
        return "addnewsview";
    }

    @PostMapping(value = "/addnewspaper")
    public String addNewNewspaper(@ModelAttribute Newsletters newsletters, Subscriber subscriber) {
        String insertSQLNews = "INSERT INTO newsletter (company, text, adminId)VALUES(:company, :text, :adminId)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("company", newsletters.getCompany());
        parameters.addValue("text", newsletters.getText());
        parameters.addValue("adminId", newsletters.getAdminId());
        namedParameterJdbcTemplate.update(insertSQLNews, parameters);

        //  String selectSQLSubscriberMail = "SELECT * FROM subscriber WHERE company =:myCompany";
        //MapSqlParameterSource parameterss = new MapSqlParameterSource();
        //parameters.addValue("myCompany", newsletters.getCompany());
        List<Subscriber> subscribers = namedParameterJdbcTemplate.query("SELECT * FROM subscriber", new SubscriberRowMapper());
        for (Subscriber s : subscribers) {
            if (s.getCompany().equals(newsletters.getCompany())) {
                System.out.println("1");
                sendEmail.sendMail(s.getEmail(), "A new newspaper has that you are subscribed to has been added!", "A newspaper from " + newsletters.getCompany()+ " has been added to the webapp!");

            }


        }//End addnewspaper
        return "redirect:/news";
    }
    @GetMapping(value="/newseditform/{id}")
    public String showEditFormByNewsId(@PathVariable String id, Model model){
        String selectSQLNewsById = "SELECT * FROM newsletter WHERE id =:myid";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("myid", id);

        Newsletters newsletters =  namedParameterJdbcTemplate.queryForObject(selectSQLNewsById, parameters, new NewsletterRowMapper());

        model.addAttribute("newsletters", newsletters);
        return "editNewsForm";

    }//End admineditform/{id}
    @PostMapping(value = "/updatenews")
    public String updateNewsletters(@ModelAttribute Newsletters newsletters){

        String updateSQLNews = "UPDATE newsletter SET company=:company, text=:text WHERE id=:myid";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("myid", newsletters.getId());
        parameters.addValue("company", newsletters.getCompany());
        parameters.addValue("text", newsletters.getText());
        namedParameterJdbcTemplate.update(updateSQLNews, parameters);
        return "redirect:/news";
    }//End updateNewsletters
    @GetMapping(value = "/deletenews/{id}")
    public String deleteNewspaperById(@PathVariable String id){

        String deleteNewspaperSQL = "DELETE FROM newsletter WHERE id =:myid";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("myid", id);
        namedParameterJdbcTemplate.update(deleteNewspaperSQL, parameters);
        return "redirect:/news";
    }
}
