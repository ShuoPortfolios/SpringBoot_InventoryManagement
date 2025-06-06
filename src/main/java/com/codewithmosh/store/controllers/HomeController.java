package com.codewithmosh.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model  model) {
        model.addAttribute("name", "Shuo");

        return "index.html";
    }

//    @RequestMapping("/hello")
//    public String sayHello(){
//        return "index.html";
//    }
}
