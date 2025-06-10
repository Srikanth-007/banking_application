
package com.cts.CBLOS.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.model.User;
import com.cts.CBLOS.repository.LoanApplicationRepository;
import com.cts.CBLOS.service.AuthenticationService;
import com.cts.CBLOS.service.UserService;
import jakarta.servlet.http.HttpSession;
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authService;
    
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        boolean isAuthenticated = authService.authenticate(email, password);
        System.out.println("Authentication Result for email " + email + ": " + isAuthenticated);
        if (isAuthenticated) {

            session.setAttribute("userEmail", email);
            System.out.println("User email stored in session: " + session.getAttribute("userEmail"));
            User user = userService.findByEmail(email); 
            if(user!=null) {
            	session.setAttribute("userId",user.getUserId());
            	Integer applicationId = (Integer) session.getAttribute("applicationId");
                if (applicationId == null) {
                    
                    LoanApplication loanApplication = loanApplicationRepository.findByUser(user);
                    if (loanApplication != null) {
                        session.setAttribute("applicationId", loanApplication.getApplicationId());
                        System.out.println("Fetched applicationId from DB: " + loanApplication.getApplicationId());
                    } else {
                        System.out.println("No loan application found for user: " + email);
                    }
                } else {
                    System.out.println("Session already contains applicationId: " + applicationId);
                }
            }
            if (user != null && userService.isAdmin(user.getEmail())) {
       
                System.out.println("Login successful for admin: " + email);
                return "redirect:/admin/dashboard"; 
            } else {
                System.out.println("Login successful for regular user: " + email);
                return "redirect:/home"; 
            }
        } else {
            model.addAttribute("error", "Invalid email or password!");
            System.out.println("Login failed for email: " + email);
            return "login"; 
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User()); 
        return "signup"; 
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        userService.saveUser(user); 
        return "redirect:/login"; 
    }
    
    @GetMapping("/home")
    public String userHome(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
          
            System.out.println("Access to /home denied: user not logged in.");
            return "redirect:/login";
        }
        model.addAttribute("message", "Welcome to your home page, " + userEmail + "!");
        System.out.println("Accessing regular user home for: " + userEmail);
        return "home"; 
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("User logged out. Session invalidated.");
        return "redirect:/login?logout"; 
    }
}
 