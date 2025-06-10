package com.cts.CBLOS.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.model.LoanApplication.LoanApplicationStatus;
import com.cts.CBLOS.model.User;
import com.cts.CBLOS.repository.UserRepository;
import com.cts.CBLOS.service.CreditEvaluationService;
import com.cts.CBLOS.service.LoanApplicationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/loan-applications")
public class LoanApplicationController {
	@Autowired
	private CreditEvaluationService creditEvaluationService;
    @Autowired
    private LoanApplicationService loanApplicationService;
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/submit")
    public String submitApplication(@ModelAttribute LoanApplication loanApplication, HttpSession session) {
    	Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "User must be logged in to submit a loan application.";
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "Invalid User!";
        }

        
        loanApplication.setUser(user);
    	LoanApplication savedApplication = loanApplicationService.submitApplication(loanApplication);
        session.setAttribute("applicationId", savedApplication.getApplicationId());
        creditEvaluationService.evaluateCredit(savedApplication.getApplicationId());
        return "redirect:/documents/upload?applicationId=" + savedApplication.getApplicationId();

    }




    @GetMapping("/track")
    public Object trackApplication(HttpSession session, Model model) {
        
        Integer applicationId = (Integer) session.getAttribute("applicationId");

        if (applicationId == null) {
            model.addAttribute("errorMessage", "Application ID not found in session!");
            return "loan-applications/error";
        }
        Optional<LoanApplication> loanApplicationOpt = loanApplicationService.getApplicationById(applicationId);
        if (loanApplicationOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Application not found!");
            return "loan-applications/error";
        }

        model.addAttribute("loanApplication", loanApplicationOpt.get());
        return "loanApplication/trackapplication"; 
    }

    @GetMapping("/status")
    public LoanApplicationStatus viewApplicationStatus(HttpSession session) {
    	Integer applicationId = (Integer) session.getAttribute("applicationId");

        if (applicationId == null) {
            return null; 
        }
        LoanApplication loanApplication = loanApplicationService.trackApplicationStatus(applicationId);
        return loanApplication != null ? loanApplication.getStatus() : null;
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("loanApplication", new LoanApplication()); 
        return "loanApplication/submitapplication";

    }
}



