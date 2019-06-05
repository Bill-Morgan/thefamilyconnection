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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    private UtilitiesController utilitiesController;

    private HashMap<Integer, String> buildAllPeopleHashMap(){
        HashMap<Integer, String> allPeopleHashMap = new HashMap<>();
        for (User eachUser : userDAO.findAll()) {
            allPeopleHashMap.put(eachUser.getId(), eachUser.getFullNameBday());
        }
        return allPeopleHashMap;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String adminSelectUser(Model model){
        if (!UtilitiesController.isLoggedIn()) {return ("redirect:/login");}
        User user = userDAO.findOne(UserController.getUserID());
        model.addAttribute("user", user);
        model.addAttribute("allUsers", buildAllPeopleHashMap());
        model.addAttribute("editUserName", user.getFullName());
        model.addAttribute("userName", user.getFullName());
        model.addAttribute("adminLevel", user.getAdmin());
        model.addAttribute("formAction", "/admin/userUpdate");
        return "admin/index";
    }

    @RequestMapping(value="", method = RequestMethod.POST  )
    public String processAdminSelectUser(Model model, @RequestParam Integer editUser){
        User adminUser = userDAO.findOne(UserController.getUserID());
        User user = userDAO.findOne(editUser);
        model.addAttribute("user", user);
        model.addAttribute("editUserName", user.getFullName());
        model.addAttribute("allUsers", buildAllPeopleHashMap());
        model.addAttribute("adminLevel", user.getAdmin());
        model.addAttribute("userName", adminUser.getFullName());
        model.addAttribute("adminLevel", adminUser.getAdmin());
        model.addAttribute("formAction", "/admin/userUpdate");
        return "admin/index";

    }

    @RequestMapping(value="userUpdate", method = RequestMethod.POST)
    public String processAdminUserUpdate(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model) {
        if (!UtilitiesController.isLoggedIn()) {return ("redirect:/login");}
        if (errors.hasErrors()) {
            User adminUser = userDAO.findOne(UserController.getUserID());
            model.addAttribute("user", user);
            model.addAttribute("editUserName", user.getFullName());
            model.addAttribute("allUsers", buildAllPeopleHashMap());
            model.addAttribute("adminLevel", user.getAdmin());
            model.addAttribute("userName", adminUser.getFullName());
            model.addAttribute("adminLevel", adminUser.getAdmin());
            model.addAttribute("formAction", "/admin/userUpdate");
            return "admin/index";
        }
        user.setFather(userDAO.findOne(father));
        user.setMother(userDAO.findOne(mother));
        user.setSpouse(userDAO.findOne(spouse));
        userDAO.save(user);
        return("redirect:/admin");
    }
}
