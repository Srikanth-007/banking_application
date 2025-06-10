package com.cts.CBLOS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.CBLOS.model.Disbursement;
import com.cts.CBLOS.service.DisbursementService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/disbursement")
public class DisbursementController {
	@Autowired
    private DisbursementService disbursementService;

    @GetMapping("/viewDisbursement")

    public String trackDisbursement(HttpSession session, Model model) {
    	Integer applicationId = (Integer) session.getAttribute("applicationId");
    	session.setAttribute("applicationId", applicationId);
    	List<Disbursement> disbursements = disbursementService.getDisbursementsByApplicationId(applicationId);
        model.addAttribute("disbursements", disbursements);
        return "approval/report"; 
    }
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    @GetMapping("/scheduleDisbursement")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String showDisbursementForm(Model model) {
//        model.addAttribute("disbursement", new Disbursement());
//        return "scheduleDisbursement"; // Links to Thymeleaf template
//    }
//
//    @GetMapping("/disbursementReport")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String generateDisbursementReport(Model model) {
//        List<Disbursement> disbursements = disbursementService.getAllDisbursements();
//        model.addAttribute("disbursements", disbursements);
//        return "/adminReport";
//    }
	}
