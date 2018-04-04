package org.softuni.work.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView index(){
       return new ModelAndView("index");
    }

    @GetMapping("/about-us")
    public ModelAndView aboutUs(){
        return new ModelAndView("about-us");
    }

    @GetMapping("/business")
    public ModelAndView business(){
        return new ModelAndView("business");
    }

    @GetMapping("/career")
    public ModelAndView career(){
        return new ModelAndView("career");
    }

    @GetMapping("/javaDevTag")
    public ModelAndView javaDevTag(){
        return new ModelAndView("javaDevTag");
    }
}
