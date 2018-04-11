package org.softuni.work.homeController;

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

    @GetMapping("/business-home")
    public ModelAndView businessHome(){
        return new ModelAndView("business-home");
    }


    @GetMapping("/business-create-app")
    public ModelAndView businessCreteApp(){
        return new ModelAndView("business-create-app");
    }

    @GetMapping("/career-home")
    public ModelAndView careerHome(){
        return new ModelAndView("career-home");
    }

    @GetMapping("/career-my-profile")
    public ModelAndView careerMyProfile(){
        return new ModelAndView("career-my-profile");
    }

    @GetMapping("/career-my-projects")
    public ModelAndView carerMyProjects(){
        return new ModelAndView("career-my-projects");
    }

    @GetMapping("/admin-home")
    public ModelAndView adminHome(){
        return new ModelAndView("admin-home");
    }

    @GetMapping("/admin-workers")
    public ModelAndView adminWorkers(){
        return new ModelAndView("admin-workers");
    }

    @GetMapping("/admin-all-projects")
    public ModelAndView adminAllProjects(){
        return new ModelAndView("admin-all-projects");
    }

    @GetMapping("/admin-worker-edit")
    public ModelAndView adminWorkerEdit(){
        return new ModelAndView("admin-worker-edit");
    }

    @GetMapping("/project-manager-home")
    public ModelAndView projectManagerHome(){
        return new ModelAndView("project-manager-home");
    }

    @GetMapping("/project-manager-list-projects")
    public ModelAndView projectManagerListProjects(){
        return new ModelAndView("project-manager-list-projects");
    }

    @GetMapping("/project-manager-workers")
    public ModelAndView projectManagerWorkers(){
        return new ModelAndView("project-manager-workers");
    }

    @GetMapping("/project-manager-edit-worker")
    public ModelAndView projectManagerEditWorker(){
        return new ModelAndView("project-manager-edit-worker");
    }

    @GetMapping("/project-manager-edit-project")
    public ModelAndView projectManagerEditProject(){
        return new ModelAndView("project-manager-edit-project");
    }
}
