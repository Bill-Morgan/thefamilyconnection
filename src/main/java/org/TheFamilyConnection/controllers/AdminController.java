package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.comparators.NameComparator;
import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    private UtilitiesController utilitiesController;

    private NameComparator comparator = new NameComparator();

    private List<User> buildAllPeopleList() {
        List<User> allUsers = userDAO.findByActiveIsTrue();
        allUsers.sort(comparator);
        return allUsers;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminSelectUser(Model model) {
        return (loadIndexPage(UserController.getUserID(), "/admin/userUpdate", model));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processAdminSelectUser(Model model, @RequestParam Integer editUser) {
        return (loadIndexPage(editUser, "/admin/userUpdate", model));
    }

    @RequestMapping(value="deleteUser/{userID}", method = RequestMethod.GET)
    public String processDeleteUser(@PathVariable Integer userID, Model model) {
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        if (userDAO.findOne(UserController.getUserID()).getAdmin() < 5) {
            return ("redirect:/user");
        }
        User user = userDAO.findOne(userID);
        for (User eachUser : userDAO.findByFatherOrMotherOrSpouse(user, user, user)) {
            if (eachUser.getMother() == user) {
                eachUser.setMother(null);
            }
            if (eachUser.getFather() == user) {
                eachUser.setFather(null);
            }
            if (eachUser.getSpouse() == user) {
                eachUser.setSpouse(null);
            }
            userDAO.save(eachUser);
        }
        user.setActive(Boolean.FALSE);
        userDAO.save(user);
        model.addAttribute("alertMsg", "User Deleted");
        return (loadIndexPage(UserController.getUserID(), "/admin/userUpdate", model));
    }

    @RequestMapping(value = "userUpdate", method = RequestMethod.POST)
    public String processAdminUserUpdate(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model) {
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        if (!errors.hasErrors()) {
            user.setFather(userDAO.findOne(father));
            user.setMother(userDAO.findOne(mother));
            user.setSpouse(userDAO.findOne(spouse));
            user.setPassword(userDAO.findOne(user.getId()).getPassword());
            user.setActive(userDAO.findOne(user.getId()).getActive());
            // this prevents and admin user from altering their admin level.
            if (user.getId() ==  UserController.getUserID()) {
                user.setAdmin(userDAO.findOne(UserController.getUserID()).getAdmin());
            }
            userDAO.save(user);
        }
        return(loadIndexPage(user.getId(), "/admin/userUpdate", model));
    }

    @RequestMapping(value = "addUser", method = RequestMethod.GET)
    public String displayNewUserForm(Model model) {
        return (loadIndexPage(0, "/admin/addUser", model));
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String processNewUserForm(@ModelAttribute @Valid User user, Errors errors,
                                    @RequestParam Integer mother,
                                    @RequestParam Integer father,
                                    @RequestParam Integer spouse,
                                    Model model) {
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        if (!errors.hasErrors()) {
            user.setFather(userDAO.findOne(father));
            user.setMother(userDAO.findOne(mother));
            user.setSpouse(userDAO.findOne(spouse));
            user.setActive(Boolean.TRUE);
            userDAO.save(user);
            model.addAttribute("alertMsg", "New User Saved");
            return(loadIndexPage(user.getId(), "", model));

        }
        model.addAttribute("alertMsg", "Save failed.  Please correct errors.");
        return (loadIndexPage(0, "/admin/addUser", model));
    }

    private String loadIndexPage(Integer userID, String formAction, Model model) {
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        if (userDAO.findOne(UserController.getUserID()).getAdmin() < 5) {
            return ("redirect:/user");
        }
        User adminUser = userDAO.findOne(UserController.getUserID());
        User user;
        if (userID != 0) {
            user = userDAO.findOne(userID);
        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        model.addAttribute("motherID", user.getMotherId());
        model.addAttribute("fatherID", user.getFatherId());
        model.addAttribute("spouseID", user.getSpouseId());
        model.addAttribute("editUserName", user.getFullName());
        model.addAttribute("allUsers", buildAllPeopleList());
        model.addAttribute("userName", adminUser.getFullName());
        model.addAttribute("adminLevel", adminUser.getAdmin());
        model.addAttribute("adminID", UserController.getUserID());
        model.addAttribute("formAction", formAction);
        return "admin/index";
    }

}

