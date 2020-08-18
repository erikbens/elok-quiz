package com.loyaltypartner.elok.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirections
 */
@Controller
public class HomeController {
    
    @RequestMapping(value = "/api-docs")
    public String swaggerDocs() {
        return "redirect:swagger-ui.html";
    }
    
    @RequestMapping(value = "/")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}
