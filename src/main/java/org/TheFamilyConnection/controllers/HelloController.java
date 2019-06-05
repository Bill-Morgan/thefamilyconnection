package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Query;
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


    @RequestMapping(value="")
    public String index()   throws ParseException  {
        doit();
        return "redirect:/user";
    }

    @RequestMapping(value="logout")
    public String logout()  {
        UserController.setUserID(-1);
        return("redirect:/");
    }


    @RequestMapping(value="login", method = RequestMethod.GET)
    public String displayLoginForm(Model model)  {
        model.addAttribute("action", "Login");
        return("login");
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public String processLoginForm(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model)  {
        UserController.setUserID(0);
        for (User users : userDAO.findAll()) {
            if (!User.isNullOrEmpty(users.getPrimaryEmail()) && !User.isNullOrEmpty(users.getPassword())) {
                if (users.getPrimaryEmail().toLowerCase().equals(username.toLowerCase()) & users.getPassword().equals(password)) {
                    UserController.setUserID(users.getId());
                    return ("redirect:/user");
                }
            }
        }

        model.addAttribute("errorMsg", "Username or password incorrect");
        model.addAttribute("action", "Login");
        return("login");
    }

    public void doit()   throws ParseException {

        String csvFile = "/kalaher.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] userData = line.split(cvsSplitBy);
   //             primary_email,dob,dod,cfname,cmname,clname,c_suffix,bfname,bmname,blname,b_suffix
                User user = new User();
                user.setPrimaryEmail(userData[0]);
                if (userData[1].contains("/")) {
                    user.setDob(formatDate(userData[1]));
                }
                if (userData[2].contains("/")) {
                    user.setDod(formatDate(userData[2]));
                }
                for (Integer i = 3; i < 10; i++){
                    if (userData[i].length()>1) {
                        userData[i] = userData[i].substring(0, 1).toUpperCase() + userData[i].substring(1).toLowerCase();
                    }
                }
                user.setcFName(userData[3]);
                user.setcMName(userData[4]);
                user.setcLName(userData[5]);
                user.setcSuffix(userData[6]);
                user.setbFName(userData[7]);
                user.setbMName(userData[8]);
                user.setbLName(userData[9]);
                userDAO.save(user);


                System.out.println(user.getPrimaryEmail() + " " + user.getDob() + " " + user.getDod());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Date formatDate(String inDate)  throws ParseException {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        dt = sdf.parse(inDate);
        return dt;
    }

}
