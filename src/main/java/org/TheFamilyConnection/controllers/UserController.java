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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailAddressDao emailAddressDao;

    private NameComparator comparator = new NameComparator();


    //private String userIDKey = new String("userID");
    private Integer userID = new Integer("-1");

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create a session object if it is already not  created.
        HttpSession session = request.getSession(true);


        // Get session creation time.
        Date createTime = new Date(session.getCreationTime());

        // Get last access time of this web page.
        Date lastAccessTime = new Date(session.getLastAccessedTime());


        if (session.isNew()) {
            session.setAttribute("userIDKey", "userID");
        }
        this.userID = (Integer) session.getAttribute("userIDKey");
    }

    public void setUserID(Integer userID, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        session.setAttribute("userIDKey", userID);
        //UserController.userID = userID;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        userID = -1;
        HttpSession session = request.getSession(true);
        session.invalidate();
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

    private String buildProfileModel(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!UtilitiesController.isLoggedIn(request, response)) {
            return ("redirect:/login");
        }
        User user = userDAO.findOne(UtilitiesController.getUserID(request, response));
        model.addAttribute("user", user);
        model.addAttribute("allUsers", getAllUsers(0));
        model.addAttribute("allMothers", getAllUsers(2));
        model.addAttribute("allFathers", getAllUsers(1));
        model.addAttribute("formAction", "");
        model.addAttribute("adminUser", user);
        return "user/profile";
    }

    @RequestMapping(value = "")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return ("redirect:/user/profile");
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String displayUserProfileForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        return (buildProfileModel(model, request, response));
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!UtilitiesController.isLoggedIn(request, response)) {
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
        return (buildProfileModel(model, request, response));
    }
}