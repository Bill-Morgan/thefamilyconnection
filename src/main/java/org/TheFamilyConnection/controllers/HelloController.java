package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("lc")
public class HelloController {

    public static Boolean logedIn = Boolean.FALSE;

    @RequestMapping(value="")
    public String index() {

        if (!logedIn) {
            return ("redirect:login");
        }
        return "index";
    }

    @RequestMapping(value="profile")
    public String displayProfile(Model model){

        if (!logedIn) {
            return ("redirect:login");
        }

        return ("users/profile");
    }

    @RequestMapping(value="login", method = RequestMethod.GET)
    public String displayLoginForm(Model model)  {

        return("users/login");
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public String processLoginForm(@RequestParam String username,@RequestParam String password)  {


        if (username.equals("billm") && password.equals("password123*")) {
                logedIn = Boolean.TRUE;
                return ("redirect:profile");
        }
        return("users/login");
    }

}
