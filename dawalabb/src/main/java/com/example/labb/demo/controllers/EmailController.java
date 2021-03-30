package com.example.labb.demo.controllers;

import com.example.labb.demo.service.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailController {

    @Autowired
    SendEmail sendEmail;

    @RequestMapping("/sendmail")
    public String sendMail(Model model){
        model.addAttribute("confirmation", "The email is sent!");
        sendEmail.sendMail("h19strom@du.se", "Testar email från spring", "Hej hej");
        return "redirect:/news";
    }


}
