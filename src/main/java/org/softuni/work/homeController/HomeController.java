package org.softuni.work.homeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/logout")
    public ModelAndView logout(@RequestParam(required = false, name = "logout") String logout, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("redirect:/");
        if(logout != null) redirectAttributes.addFlashAttribute("logout", logout);
        return modelAndView;
    }

    @PostMapping("/business-logout")
    public ModelAndView businessLogout(@RequestParam(required = false, name = "business-logout") String logout, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("redirect:/");
        if(logout != null) redirectAttributes.addFlashAttribute("logout", logout);
        return modelAndView;
    }
}
