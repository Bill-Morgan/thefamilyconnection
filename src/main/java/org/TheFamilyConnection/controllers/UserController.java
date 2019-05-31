package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    public static Integer userID;

    public static String isLoggedIn(){
        if (userID.equals(null)) {
            return ("redirect:login");
        }
        return ("");
    }

    @RequestMapping(value="")
    public String index(){
        return ("redirect:/user/profile");
    }

    @RequestMapping(value="profile", method = RequestMethod.GET)
    public String displayUserProfileForm(Model model) {
        isLoggedIn();
        model.addAttribute("user", userDAO.findOne(userID));
        return "user/profile";
    }

    @RequestMapping(value="profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User newUser,
                                         Errors errors){

        if (errors.hasErrors()) {

        }

        return("redrect:/users");
    }

}
