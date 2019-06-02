package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("")
public class HelloController {

    public static String username;

    @Autowired
    private UserDAO userDAO;


    @RequestMapping(value="")
    public String index() {
        return "redirect:/user";
    }

    @RequestMapping(value="profile")
    public String displayProfile(Model model){
    return ("redirect:/user");
    }

    @RequestMapping(value="logout")
    public String logout()  {
        UserController.userID = -1;
        return("redirect:/");
    }


    @RequestMapping(value="login", method = RequestMethod.GET)
    public String displayLoginForm(Model model)  {
        model.addAttribute("action", "Login");
        return("login");
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public String processLoginForm(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model)  {
        UserController.userID = null;
        for (User users : userDAO.findAll()) {
            if (users.getPrimaryEmail().toLowerCase().equals(username.toLowerCase()) & users.getPassword().equals(password)) {
                UserController.setCurrentUser(users);
                return ("redirect:/user");
            }
        }

        model.addAttribute("errorMsg", "Username or password incorrect");
        model.addAttribute("action", "Login");
        return("login");
    }

}
