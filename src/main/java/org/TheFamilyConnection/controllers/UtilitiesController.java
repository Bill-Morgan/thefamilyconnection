package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class UtilitiesController {

    @Autowired
    private UserDAO userDAO;

    public HashMap<Integer, String> buildAllPeopleHashMap(){
        HashMap<Integer, String> allPeopleHashMap = new HashMap<>();
        for (User eachUser : userDAO.findAll()) {
            allPeopleHashMap.put(eachUser.getId(), eachUser.getFullName());
        }
        return allPeopleHashMap;
    }

    public static Boolean isLoggedIn(){
        if (UserController.getUserID() <= 0) {
            return (false);
        }
        return (true);
    }

    public void updateUser(User user, Integer userID) {
        User updateUser = userDAO.findOne(userID);
        updateUser.setbFName(user.getbFName());
        updateUser.setbLName(user.getbLName());
        updateUser.setbMName(user.getbMName());
        updateUser.setbSuffix(user.getbSuffix());
        updateUser.setcFName(user.getcFName());
        updateUser.setcLName(user.getcLName());
        updateUser.setcMName(user.getcMName());
        updateUser.setcSuffix(user.getcSuffix());
        updateUser.setAnniversary(user.getAnniversary());
        updateUser.setAddress(user.getAddress());
        updateUser.setCity(user.getCity());
        updateUser.setDob(user.getDob());
        updateUser.setDod(user.getDod());
        updateUser.setPob(user.getPob());
        updateUser.setPrimaryEmail(user.getPrimaryEmail());
        updateUser.setState(user.getState());
        updateUser.setZip(user.getZip());
        updateUser.setAnniversary(user.getAnniversary());
//        updateUser.setMother();
//        updateUser.setFather(userDAO.findOne(user.getFather()));
  //      updateUser.setSpouse(userDAO.findOne(user.getSpouse()));
        userDAO.save(updateUser);
    }


}
