package org.TheFamilyConnection.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value="")
    public String index(Model model) {

        if (!HelloController.logedIn) {
            return ("redirect:/");
        }
        model.addAttribute("username", HelloController.username);
        return "user/profile";
    }

}
