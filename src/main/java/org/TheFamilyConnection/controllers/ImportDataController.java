package org.TheFamilyConnection.controllers;

import org.TheFamilyConnection.models.User;
import org.TheFamilyConnection.models.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("importdata")
public class ImportDataController {

    @Autowired
    private UserDAO userDAO;


    @RequestMapping(value="{csvFile}")
    @ResponseBody
    public String doit(@PathVariable String csvFile) {

        csvFile += ".csv";
        String line = "";
        String cvsSplitBy = ",";
        Integer j = 0;

        if (true) {
            return ("feature not available");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] userData = line.split(cvsSplitBy);
/*                for (int k = 1; k < userData.length; k++) {
                    userData[k].replace("\"", "");
                    userData[k].replace("NULL", "");
                }
*/                User user = new User();
                user.setAddress(userData[1]);
                user.setAnniversary(formatDate(userData[2]));
                user.setbFName(userData[3]);
                user.setbLName(userData[4]);
                user.setbMName(userData[5]);
                user.setbSuffix(userData[6]);
                user.setcFName(userData[7]);
                user.setcLName(userData[8]);
                user.setcMName(userData[9]);
                user.setcSuffix(userData[10]);
                user.setCity(userData[11]);
                user.setPassword(userData[12]);
                user.setPob(userData[13]);
                user.setPrimaryEmail(userData[14]);
                user.setState(userData[15]);
                user.setZip(userData[16]);
                user.setDob(formatDate(userData[20]));
                user.setDod(formatDate(userData[21]));
                user.setAdmin(0);
                user.setActive(Boolean.TRUE);
                user.setGender(0);
                userDAO.save(user);
                j++;

                System.out.println(user.getPrimaryEmail() + " " + user.getDob() + " " + user.getDod());

            }

        } catch (IOException e) {

            return ("Error.  Could not open data file " + csvFile);
        }
        return ( j.toString() + " records imported");
    }

    private Date formatDate(String inDate) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dt = sdf.parse(inDate);
        }  catch (ParseException e) {
            dt = null;
        }
        return dt;
    }


}
