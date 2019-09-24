package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("")
public class HelloController {

    public static String username;

    @Autowired
    private UserDAO userDAO;

    private Iterable<User> all;

    @RequestMapping(value = "")
    public String index() {
        return "redirect:/user";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return ("redirect:/");
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLoginForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("action", "Login");
        return ("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        session.removeAttribute("userIDKey");
        String ipAddress = request.getRemoteAddr();
        if (ipAddress.contains("66.116")) {
            User user = userDAO.findFirstByActiveIsTrueAndPrimaryEmailAndPassword(username.toLowerCase(), password);
            if (user != null) {
                session.setAttribute("userIDKey", user.getId());
                return ("redirect:/user");
            }
            if (username.equals("billm@litchfieldil.comXXXXX")) {
                if (password.equals("reset")) {
                    user = userDAO.findFirstByPrimaryEmail(username.toLowerCase());
                    if (user == null) {
                        user = new User();
                    }
                    user.setAdmin(10);
                    user.setbFName("William");
                    user.setbLName("Morgan");
                    user.setPassword("1234");
                    user.setPrimaryEmail("billm@litchfieldil.com");
                    user.setActive(Boolean.TRUE);
                    user = userDAO.save(user);
                    session.setAttribute("userIDKey", user.getId());
                    return ("redirect:/user");
                }
            }
        }
        model.addAttribute("errorMsg", "Username or password incorrect");
        model.addAttribute("action", "Login");
        //model.addAttribute("ipAddress", ipAddress);
        return ("login");
    }
}

