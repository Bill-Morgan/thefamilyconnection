package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.comparators.NameComparator;
import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    private int male = 2;

    private int female = 1;

    private NameComparator comparator = new NameComparator();

    private List<User> buildAllPeopleList(Integer gender) {
        List<User> allUsers = new ArrayList<>();
        if (gender == 0) {
            allUsers = userDAO.findByActiveIsTrue();
        } else {
            allUsers = userDAO.findByActiveIsTrueAndGenderNot(gender);
        }
        allUsers.sort(comparator);
        return allUsers;
    }

    private void setUserSpouse(User user){
        List<User> userXSpouse = userDAO.findBySpouse(user);
        for (User eachXSpouse : userXSpouse) {
            eachXSpouse.setSpouse(null);
            eachXSpouse.setAnniversary(null);
            userDAO.save(eachXSpouse);
        }
        User userSpouse = user.getSpouse();
        if (userSpouse != null) {
            if (userSpouse.getSpouse() != null) {
                User userSpouseXSpouse = userSpouse.getSpouse();
                userSpouseXSpouse.setAnniversary(null);
                userSpouseXSpouse.setSpouse(null);
                userDAO.save(userSpouseXSpouse);
            }
            userSpouse.setSpouse(user);
            userSpouse.setAnniversary(user.getAnniversary());
            userDAO.save(userSpouse);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminSelectUser(Model model, HttpServletRequest request, HttpServletResponse response) {
        return (loadIndexPage(UtilitiesController.getUserID(request, response), "/admin/userUpdate", model, request, response));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processAdminSelectUser(Model model, @RequestParam Integer editUser,
                                         HttpServletRequest request, HttpServletResponse response) {
        return (loadIndexPage(editUser, "/admin/userUpdate", model, request, response));
    }

    @RequestMapping(value = "edit/{userID}", method = RequestMethod.GET)
    public String displayEditUser(@PathVariable Integer userID, Model model,
                                  HttpServletRequest request, HttpServletResponse response) {
        return (loadIndexPage(userID, "/admin/userUpdate", model, request, response));
    }

    @RequestMapping(value = "deleteUser/{userID}", method = RequestMethod.GET)
    public String processDeleteUser(@PathVariable Integer userID, Model model,
                                    HttpServletRequest request, HttpServletResponse response) {
        if (!UtilitiesController.isLoggedIn(request, response)) {
            return ("redirect:/login");
        }
        if (userDAO.findOne(UtilitiesController.getUserID(request, response)).getAdmin() < 5) {
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
        return (loadIndexPage(UtilitiesController.getUserID(request, response), "/admin/userUpdate", model, request, response));
    }

    @RequestMapping(value = "userUpdate", method = RequestMethod.POST)
    public String processAdminUserUpdate(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model,  HttpServletRequest request, HttpServletResponse response) {
        if (!UtilitiesController.isLoggedIn(request, response)) {
            return ("redirect:/login");
        }
        if (!errors.hasErrors()) {
            user.setFather(userDAO.findOne(father));
            user.setMother(userDAO.findOne(mother));
            user.setSpouse(userDAO.findOne(spouse));
            user.setPassword(userDAO.findOne(user.getId()).getPassword());
            user.setActive(userDAO.findOne(user.getId()).getActive());
            // this prevents and admin user from altering their admin level.
            if (user.getId() ==  UtilitiesController.getUserID(request, response)) {
                user.setAdmin(userDAO.findOne(UtilitiesController.getUserID(request, response)).getAdmin());
            }
            userDAO.save(user);
            setUserSpouse(user);
        }
        return(loadIndexPage(user.getId(), "/admin/userUpdate", model, request, response));
    }

    @RequestMapping(value = "addUser", method = RequestMethod.GET)
    public String displayNewUserForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        return (loadIndexPage(0, "/admin/addUser", model, request, response));
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String processNewUserForm(@ModelAttribute @Valid User user, Errors errors,
                                    @RequestParam Integer mother,
                                    @RequestParam Integer father,
                                    @RequestParam Integer spouse,
                                    Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!UtilitiesController.isLoggedIn(request, response)) {
            return ("redirect:/login");
        }
        if (!errors.hasErrors()) {
            user.setFather(userDAO.findOne(father));
            user.setMother(userDAO.findOne(mother));
            user.setSpouse(userDAO.findOne(spouse));
            user.setActive(Boolean.TRUE);
            userDAO.save(user);
            setUserSpouse(user);
            model.addAttribute("alertMsg", "New User Saved");
            return(loadIndexPage(user.getId(), "/admin/userUpdate", model, request, response));

        }
        model.addAttribute("alertMsg", "Save failed.  Please correct errors.");
        return (loadIndexPage(0, "/admin/addUser", model, request, response));
    }

    private String loadIndexPage(Integer userID, String formAction, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!UtilitiesController.isLoggedIn(request, response)) {
            return ("redirect:/login");
        }
        if (userDAO.findOne(UtilitiesController.getUserID(request, response)).getAdmin() < 5) {
            return ("redirect:/user");
        }
        User adminUser = userDAO.findOne(UtilitiesController.getUserID(request, response));
        User user;
        if (userID != 0) {
            user = userDAO.findOne(userID);
        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        model.addAttribute("allMothers", buildAllPeopleList(2));
        model.addAttribute("allFathers", buildAllPeopleList(1));
        model.addAttribute("allUsers", buildAllPeopleList(0));
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("formAction", formAction);
        return "admin/index";
    }

}

