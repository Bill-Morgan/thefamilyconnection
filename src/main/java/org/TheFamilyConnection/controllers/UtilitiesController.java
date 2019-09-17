package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UtilitiesController {

    @Autowired
    private UserDAO userDAO;

    public static Boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return (false);
        }
        if (session.getAttribute("userIDKey") == null) {
            return (false);
        }
        return (true);
    }

    public static Integer getUserID(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("userIDKey") != null) {
            return (Integer) (session.getAttribute("userIDKey"));
        }
        return (-1);
    }

}
