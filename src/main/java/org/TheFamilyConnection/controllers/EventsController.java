package org.TheFamilyConnection.controllers;

import com.fasterxml.jackson.databind.node.BooleanNode;
import org.TheFamilyConnection.comparators.AnniversaryComparator;
import org.TheFamilyConnection.comparators.BirthdayComparator;
import org.TheFamilyConnection.comparators.GenderComparator;
import org.TheFamilyConnection.models.Anniversary;
import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("events")
public class EventsController {

    @Autowired
    private UserDAO userDAO;

    private UtilitiesController utilitiesController;

    private BirthdayComparator comparatorBD = new BirthdayComparator();

    private AnniversaryComparator comparatorAnn = new AnniversaryComparator();

    private List<User> getAllUsers(Integer month) {
        List<User> allUsers = userDAO.findByActiveIsTrueAndDobNotNull();
        if (month == 0) { return allUsers;}
        List<User> filteredUsers = new ArrayList<>();
        for (User eachUser : allUsers) {
            if (Integer.parseInt(eachUser.getBirthMonth()) == month) {
                filteredUsers.add(eachUser);
            }
        }
        filteredUsers.sort(comparatorBD);
        return filteredUsers;
    }

    private List<User> getAllWSpouse(Integer month) {
        List<User> allUsers = userDAO.findByActiveIsTrueAndSpouseNotNullAndAnniversaryNotNull();
        List<User> filteredUsers = new ArrayList<>();
        for (User eachUser : allUsers) {
            if (Integer.parseInt(eachUser.getAnniversaryMonth()) == month) {
                filteredUsers.add(eachUser);
            }
        }
        return filteredUsers;
    }

    private List<Anniversary> getAnniversaries(Integer month){
        List<Anniversary> anniversaries = new ArrayList<>();
        List<User> allUsers = getAllWSpouse(month);
        for (int i = 0; i < allUsers.size(); i++) {
            anniversaries.add(new Anniversary(allUsers.get(i)));
            allUsers.remove(allUsers.get(i).getSpouse());
        }
        anniversaries.sort(comparatorAnn);
        return anniversaries;
    }

    private Integer getCurrentMonthInt(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    @RequestMapping(value="")
    public String index(){
        return "redirect:/events/calendar";
    }

    @RequestMapping(value="calendar", method = RequestMethod.GET)
    public String displayCalendarDefault(Model model, HttpServletRequest request, HttpServletResponse response) {
        return buildEventsPageModel(model, getCurrentMonthInt() + 1, request, response);
    }

    @RequestMapping(value="calendar/{theMonth}", method = RequestMethod.GET)
    public String displayCalendarMonth(@PathVariable String theMonth,
                        Model model, HttpServletRequest request, HttpServletResponse response){
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
        return buildEventsPageModel(model, theMonthInt, request, response);
    }

    private String buildEventsPageModel(Model model, Integer theMonthInt,
                                        HttpServletRequest request, HttpServletResponse response) {
        List<String> theMonths = Arrays.asList("December", "January", "February", "March", "April",
                                            "May", "June", "July", "August", "September", "October",
                                            "November", "December", "January");
        User adminUser = userDAO.findOne(UtilitiesController.getUserID(request, response));
        model.addAttribute("user", adminUser);
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("theMonths", theMonths);
        model.addAttribute("lastMonth", theMonths.get(theMonthInt - 1));
        model.addAttribute("thisMonth", theMonths.get(theMonthInt));
        model.addAttribute("nextMonth", theMonths.get(theMonthInt + 1));
        model.addAttribute("anniversaries", getAnniversaries(theMonthInt));
        model.addAttribute("theMonthInt", theMonthInt);
        model.addAttribute("allUsers", getAllUsers(theMonthInt));
        return "events/index";
    }

}
