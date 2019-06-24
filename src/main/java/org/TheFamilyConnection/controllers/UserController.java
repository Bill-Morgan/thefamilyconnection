package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.comparators.NameComparator;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailAddressDao emailAddressDao;

    private NameComparator comparator = new NameComparator();

    // this is the id of the currently logged in user
    private static Integer userID = -1;

    public static Integer getUserID() {
        return userID;
    }

    public static void setUserID(Integer userID) {
        UserController.userID = userID;
    }

    private List<User> getAllUsers(Integer gender) {
        List<User> allUsers = new ArrayList<>();
        if (gender == 0) {
            allUsers = userDAO.findByActiveIsTrue();
        } else {
            allUsers = userDAO.findByActiveIsTrueAndGenderNot(gender);
        }
        allUsers.sort(comparator);
        return allUsers;
    }

/*
    private List<User> getAllUsers() {
        List<User> allUsers = userDAO.findByActiveIsTrue();
        allUsers.sort(comparator);
        return allUsers;
    }

 */

    private String buildProfileModel(Model model) {
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        User user = userDAO.findOne(userID);
        model.addAttribute("user", user);
        model.addAttribute("allUsers", getAllUsers(0));
        model.addAttribute("allMothers", getAllUsers(2));
        model.addAttribute("allFathers", getAllUsers(1));
        model.addAttribute("formAction", "");
        model.addAttribute("adminUser", user);
        return "user/profile";
    }

    @RequestMapping(value = "")
    public String index() {
        return ("redirect:/user/profile");
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String displayUserProfileForm(Model model) {
        return (buildProfileModel(model));
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model) {
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        if (!errors.hasErrors()) {
            user.setMother(userDAO.findOne(mother));
            user.setFather(userDAO.findOne(father));
            user.setSpouse(userDAO.findOne(spouse));
            user.setPassword(userDAO.findOne(user.getId()).getPassword());
            user.setAdmin(userDAO.findOne(user.getId()).getAdmin());
            user.setActive(Boolean.TRUE);
            userDAO.save(user);
        }
        return (buildProfileModel(model));
    }
}