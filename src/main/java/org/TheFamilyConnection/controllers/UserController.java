package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.EmailAddressDao;
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
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailAddressDao emailAddressDao;

    // this is the id of the currently logged in user
    private static Integer userID = -1;

    public static Integer getUserID() {
        return userID;
    }

    public static void setUserID(Integer userID) {
        UserController.userID = userID;
    }

    private HashMap<Integer, String> buildAllPeopleHashMap(){
        HashMap<Integer, String> allPeopleHashMap = new HashMap<>();
        for (User eachUser : userDAO.findAll()) {
            allPeopleHashMap.put(eachUser.getId(), eachUser.getFullName());
        }
        return allPeopleHashMap;
    }

    @RequestMapping(value="")
    public String index(){
        return ("redirect:/user/profile");
    }

    @RequestMapping(value="profile", method = RequestMethod.GET)
    public String displayUserProfileForm(Model model) {
        if (!UtilitiesController.isLoggedIn()) {return ("redirect:/login");}
        User user = userDAO.findOne(userID);
        model.addAttribute("user", user);
        model.addAttribute("userName", user.getFullName());
        model.addAttribute("allUsers", buildAllPeopleHashMap());
        model.addAttribute("motherID", user.getMotherId());
        model.addAttribute("fatherID", user.getFatherId());
        model.addAttribute("spouseID", user.getSpouseId());
        model.addAttribute("formAction", "");
        model.addAttribute("adminLevel", user.getAdmin());
        return "user/profile";
    }

    @RequestMapping(value="profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model) {
        if (!UtilitiesController.isLoggedIn()) {return ("redirect:/login");}
        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("userName", user.getFullName());
            model.addAttribute("motherID", user.getMotherId());
            model.addAttribute("fatherID", user.getFatherId());
            model.addAttribute("spouseID", user.getSpouseId());
            model.addAttribute("allUsers", buildAllPeopleHashMap());
            model.addAttribute("adminLevel", user.getAdmin());
            model.addAttribute("formAction", "");
            return "user/profile";
        }
        user.setMother(userDAO.findOne(mother));
        user.setFather(userDAO.findOne(father));
        user.setSpouse(userDAO.findOne(spouse));
        userDAO.save(user);
        return("redirect:/admin");
    }
}
