package org.TheFamilyConnection.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class HelloController {

    public static Boolean logedIn = Boolean.FALSE;
    public static String username;

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

    @RequestMapping(value="logout")
    public String logout()  {
        logedIn = Boolean.FALSE;
        return("redirect:/");
    }


    @RequestMapping(value="login", method = RequestMethod.GET)
    public String displayLoginForm(Model model)  {

        return("login");
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public String processLoginForm(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model)  {


        if (username.equals("billm") && password.equals("password123*")) {
                logedIn = Boolean.TRUE;
                this.username = username;
                model.addAttribute("username", this.username);
                return ("/user/profile");
        }
        model.addAttribute("errorMsg", "Username or password incorrect");
        return("login");
    }

}
