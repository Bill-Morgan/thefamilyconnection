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

    private static Integer userID = -1;

    public static Integer getUserID() {
        return userID;
    }

    public static void setUserID(Integer userID) {
        UserController.userID = userID;
    }

//    public static void setCurrentUser(User user) {
  //      userID = user.getId();
    //}

    private HashMap<Integer, String> buildAllPeopleHashMap(){
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
        if (!UtilitiesController.isLoggedIn()) {return ("redirect:/login");}
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
        model.addAttribute("userName", user.getFullName());
        model.addAttribute("allUsers", buildAllPeopleHashMap());
        model.addAttribute("motherID", motherID);
        model.addAttribute("fatherID", fatherID);
        model.addAttribute("spouseID", spouseID);
        model.addAttribute("formAction", "");
        model.addAttribute("adminLevel", user.getAdmin());
        return "user/profile";
    }

    @RequestMapping(value="profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User user, Errors errors,
                                         @RequestParam Integer mother,
                                         @RequestParam Integer father,
                                         @RequestParam Integer spouse,
                                         Model model) {
        if (!UtilitiesController.isLoggedIn()) {return ("redirect:/login");}
        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("userName", user.getFullName());
            model.addAttribute("allUsers", buildAllPeopleHashMap());
            model.addAttribute("adminLevel", user.getAdmin());
            model.addAttribute("formAction", "");
            return "user/profile";
        }
        /*
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
         */
        user.setMother(userDAO.findOne(mother));
        user.setFather(userDAO.findOne(father));
        user.setSpouse(userDAO.findOne(spouse));
        userDAO.save(user);
//        setUserID(updateUser.getId());
//        setCurrentUser(updateUser);
//        buildAllPeopleHashMap();
        return("redirect:/admin");
    }
}
