package com.example.labb.demo.controllers;

import com.example.labb.demo.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SendMailController {

    @Autowired
    SendEmailService sendEmailService;

    @RequestMapping("/sendmail")
    public String sendMail(Model model){
        model.addAttribute("confirmation", "The email is sent!");
        sendEmailService.sendMail("alexander.strom19@gmail.com", "Testar email från spring", "Hej hej");
        return "emailconfirmation";
    }


}
