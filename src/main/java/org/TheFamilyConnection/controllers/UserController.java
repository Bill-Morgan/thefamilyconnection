package org.TheFamilyConnection.controllers;

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

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailAddressDao emailAddressDao;

    public static Integer userID = -1;

    public static String userName;

    public static Boolean isLoggedIn(){
        if (userID < 0) {
            return (false);
        }
        return (true);
    }

    public static void setCurrentUser(User user) {
        userID = user.getId();
        userName = user.getFullName();
    }

    public HashMap<Integer, String> buildAllPeopleHashMap(){
        HashMap<Integer, String> allPeopleHashMap = new HashMap<>();
        for (User eachUser : userDAO.findAll()) {
            allPeopleHashMap.put(eachUser.getId(), eachUser.getFullName());
        }
        return allPeopleHashMap;
    }

    @RequestMapping(value="")
    public String index(){
        return ("redirect:/user/profile");
    }

    @RequestMapping(value="profile", method = RequestMethod.GET)
    public String displayUserProfileForm(Model model) {
        if (!isLoggedIn()) {return ("redirect:/login");}
        User user = userDAO.findOne(userID);
        Integer motherID = 0;
        Integer fatherID = 0;
        Integer spouseID = 0;
        if (user.getMother() != null) {
            motherID = (user.getMother()).getId();
        }
        if(user.getFather() != null) {
            fatherID = (user.getFather()).getId();
        }
        if (user.getSpouse() != null) {
            spouseID = (user.getSpouse()).getId();
        }
        model.addAttribute("user", user);
        model.addAttribute("userName", userName);
        model.addAttribute("allUsers", buildAllPeopleHashMap());
        model.addAttribute("motherID", motherID);
        model.addAttribute("fatherID", fatherID);
        model.addAttribute("spouseID", spouseID);
        return "user/profile";
    }

    @RequestMapping(value="profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model) {
        if (!isLoggedIn()) {return ("redirect:/login");}
        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("userName", userName);
            model.addAttribute("allUsers", buildAllPeopleHashMap());
            return "user/profile";
        }
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
        updateUser.setMother(userDAO.findOne(mother));
        updateUser.setFather(userDAO.findOne(father));
        updateUser.setSpouse(userDAO.findOne(spouse));
        userDAO.save(updateUser);
        setCurrentUser(updateUser);
        buildAllPeopleHashMap();
        return("redirect:/user");
    }
}
