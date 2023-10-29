package com.contactmanager.Controller;

import com.contactmanager.DAO.UserRepository;
import com.contactmanager.Entity.User;
import com.contactmanager.Helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    //    Home Handler
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home - Contact Manager");
        return "home";
    }

    //    About Handler
    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About - Contact Manager");
        return "about";
    }

    //    Signup Handler
    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register - Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    // Handler for registering user
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam(value = "agreement", defaultValue = "false")
                               boolean agreement, Model model, HttpSession session){

        try {
            if(!agreement){
                System.out.println("You have not agreed to the terms and conditions");
                throw new Exception("You have not agreed to the terms and conditions");
            }


            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");

            User result =  this.userRepository.save(user);


            System.out.println("Agreement " + agreement);
            System.out.println("User " + result);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
            return "signup";

        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong !! " + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }

}