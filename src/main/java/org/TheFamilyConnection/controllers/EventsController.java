package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.comparators.BirthdayComparator;
import org.TheFamilyConnection.comparators.NameComparator;
import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventsController {

    @Autowired
    private UserDAO userDAO;

    private UtilitiesController utilitiesController;

    private BirthdayComparator comparator = new BirthdayComparator();

    private List<User> buildAllPeopleList() {
        List<User> allUsers = userDAO.findByActive(Boolean.TRUE);
        List<User> filteredUsers = new ArrayList<>();
        for (User eachUser : allUsers) {
            if (eachUser.getDob() != null) {
                filteredUsers.add(eachUser);
            }
        }
        filteredUsers.sort(comparator);
        return filteredUsers;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model){
        User adminUser = userDAO.findOne(UserController.getUserID());
        model.addAttribute("adminLevel", adminUser.getAdmin());
        model.addAttribute("userName", adminUser.getFullName());
        model.addAttribute("adminID", UserController.getUserID());
        model.addAttribute("allUsers", buildAllPeopleList());
        return "events/index";
    }




}
