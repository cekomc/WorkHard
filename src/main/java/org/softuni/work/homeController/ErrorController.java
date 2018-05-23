package org.softuni.work.homeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @GetMapping("/career-error")
    public ModelAndView index(){
        return new ModelAndView("404");
    }

}
