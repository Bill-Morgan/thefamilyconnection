package org.TheFamilyConnection.controllers;

import com.fasterxml.jackson.databind.node.BooleanNode;
import org.TheFamilyConnection.comparators.BirthdayComparator;
import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("events")
public class EventsController {

    @Autowired
    private UserDAO userDAO;

    private UtilitiesController utilitiesController;

    private BirthdayComparator comparator = new BirthdayComparator();

    private List<User> getAllUsers(Integer month) {
        List<User> allUsers = userDAO.findByActiveIsTrueAndDobNotNull();
        allUsers.sort(comparator);
        if (month == 0) { return allUsers;}
        List<User> filteredUsers = new ArrayList<>();
        for (User eachUser : allUsers) {
            if (Integer.parseInt(eachUser.getBirthMonth()) == month) {
                filteredUsers.add(eachUser);
            }
        }
        return filteredUsers;
    }

    private Integer getCurrentMonthInt(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    @RequestMapping(value="")
    public String index(){
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        return "redirect:/events/calendar";
    }

    @RequestMapping(value="calendar", method = RequestMethod.GET)
    public String displayCalendarDefault(Model model) {
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        return buildEventsPageModel(model, getCurrentMonthInt() + 1);
    }

    @RequestMapping(value="calendar/{theMonth}", method = RequestMethod.GET)
    public String displayCalendarMonth(@PathVariable String theMonth,
                        Model model){
        if (!UtilitiesController.isLoggedIn()) {
            return ("redirect:/login");
        }
        Integer theMonthInt;
        try {
            theMonthInt = Integer.valueOf(theMonth);
        } catch (Exception e) {
            theMonthInt = getCurrentMonthInt() + 1;
        }
        if (theMonthInt <= 0) {
            theMonthInt = 12;
        }
        ;
        if (theMonthInt >= 13) {
            theMonthInt = 1;
        }
        return buildEventsPageModel(model, theMonthInt);
    }

    private String buildEventsPageModel(Model model, Integer theMonthInt) {
        HashMap<Integer, String> theMonths = new HashMap<>();
        theMonths.put(0, "December");
        theMonths.put(1, "January");
        theMonths.put(2, "February");
        theMonths.put(3, "March");
        theMonths.put(4, "April");
        theMonths.put(5, "May");
        theMonths.put(6, "June");
        theMonths.put(7, "July");
        theMonths.put(8, "August");
        theMonths.put(9, "September");
        theMonths.put(10, "October");
        theMonths.put(11, "November");
        theMonths.put(12, "December");
        theMonths.put(13, "January");
        User adminUser = userDAO.findOne(UserController.getUserID());
        model.addAttribute("adminLevel", adminUser.getAdmin());
        model.addAttribute("userName", adminUser.getFullName());
        model.addAttribute("adminID", UserController.getUserID());
        model.addAttribute("theMonths", theMonths);
        model.addAttribute("lastMonth", theMonths.get(theMonthInt - 1));
        model.addAttribute("thisMonth", theMonths.get(theMonthInt));
        model.addAttribute("nextMonth", theMonths.get(theMonthInt + 1));
        model.addAttribute("theMonthInt", theMonthInt);
        model.addAttribute("allUsers", getAllUsers(theMonthInt));
        return "events/index";
    }


}
