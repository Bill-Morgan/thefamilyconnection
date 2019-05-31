package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    List<User> users;


    @RequestMapping(value="")
    public String index(Model model) {

        if (!HelloController.logedIn) {
            return ("redirect:/");
        }
        model.addAttribute("username", HelloController.username);
        return "user/profile";
    }

    @RequestMapping(value="Profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User newUser,
                                         Errors errors){

        if (errors.hasErrors()) {

        }

        return("");
    }

}
