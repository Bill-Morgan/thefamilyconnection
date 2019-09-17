package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.comparators.DOBComparator;
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
import java.util.List;

@Controller
@RequestMapping("familytree")
public class FamilyTreeController {

    @Autowired
    private UserDAO userDAO;

    private DOBComparator comparator = new DOBComparator();

    public List<User> getKids(User user) {
        List<User> theKids = userDAO.findByActiveIsTrueAndMotherOrFather(user, user);
        theKids.sort(comparator);
        return theKids;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, HttpServletResponse response){
        if (!UtilitiesController.isLoggedIn(request, response)) {
            return ("redirect:/login");
        }
        User adminUser = userDAO.findOne(UtilitiesController.getUserID(request, response));
        model.addAttribute("user", adminUser);
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("theKids", getKids(adminUser));
        return "familyTree/index";
    }

    @RequestMapping(value="{userID}", method = RequestMethod.GET)
    public String displayUser(@PathVariable Integer userID, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!UtilitiesController.isLoggedIn(request, response)) {
            return ("redirect:/login");
        }
        User user = userDAO.findOne(userID);
        User adminUser = userDAO.findOne(UtilitiesController.getUserID(request, response));
        model.addAttribute("user", user);
        model.addAttribute("theKids", getKids(user));
        model.addAttribute("adminUser", adminUser);
        return "familyTree/index";
    }

}
