package org.TheFamilyConnection.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class HelloController {

    @RequestMapping(value="")
    public String index() {
        return "index";
    }

    @RequestMapping(value="profile")
    public String displayProfile(Model model){

        return ("users/profile");
    }

    @RequestMapping(value="login", method = RequestMethod.GET)
    public String displayLoginForm(Model model)  {

        return("users/login");
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public String processLoginForm(@RequestParam String username,@RequestParam String password)  {


        if (username.equals("billm") && password.equals("password")) {
                return ("redirect:profile");
        }
        return("users/login");
    }

}
