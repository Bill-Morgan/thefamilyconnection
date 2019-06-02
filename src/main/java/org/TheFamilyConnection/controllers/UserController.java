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
import java.text.SimpleDateFormat;
import java.util.Date;

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
        if (user.getcFName().length() > 0) {
            userName = user.getcFName();
        } else {
            userName = user.getbFName();
        }
        if (user.getcLName().length() > 0) {
            userName = userName + " " + user.getcLName();
        } else {
            userName = userName + " " + user.getbLName();
        }
    }

    private static Date strToDate(String inStr)throws Exception{
        if (inStr.length() == 0) { return null;}
        Date retValue = new SimpleDateFormat("yyyy-MM-dd").parse(inStr);
        return  retValue;
    }

    @RequestMapping(value="")
    public String index(){
        return ("redirect:/user/profile");
    }

    @RequestMapping(value="profile", method = RequestMethod.GET)
    public String displayUserProfileForm(Model model) {
        if (!isLoggedIn()) {return ("redirect:/login");}
        User user = userDAO.findOne(userID);
        model.addAttribute("user", user);
        model.addAttribute("dobStr", user.getDob());
        model.addAttribute("dodStr", user.getDod());
        model.addAttribute("userName", userName);
        return "user/profile";
    }

    @RequestMapping(value="profile", method = RequestMethod.POST)
    public String processUserProfileForm(@ModelAttribute @Valid User user,
                                         Errors errors,
                                         Model model,
                                         @RequestParam String dobStr,
                                         @RequestParam String dodStr)throws Exception{
        if (!isLoggedIn()) {return ("redirect:/login");}
        if (errors.hasErrors()) {
            model.addAttribute("error", "has errors");
            model.addAttribute("user", user);
            model.addAttribute("dobStr", user.getDob());
            model.addAttribute("dodStr", user.getDod());
            model.addAttribute("userName", userName);
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
        updateUser.setDob(strToDate(dobStr));
        updateUser.setDod(strToDate(dodStr));
        updateUser.setPob(user.getPob());
        updateUser.setPrimaryEmail(user.getPrimaryEmail());
        updateUser.setState(user.getState());
        updateUser.setZip(user.getZip());
        userDAO.save(updateUser);
        setCurrentUser(updateUser);
        return("redirect:/user");
    }

}
