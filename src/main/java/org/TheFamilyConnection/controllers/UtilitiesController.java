package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UtilitiesController {

    @Autowired
    private UserDAO userDAO;

    public static Boolean isLoggedIn(){
        if (UserController.getUserID() <= 0) {
            return (false);
        }
        return (true);
    }


}
