package com.cts.CBLOS.controller;
 
import java.io.IOException;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.CBLOS.model.Disbursement;
import com.cts.CBLOS.model.Document;
import com.cts.CBLOS.repository.DocumentRepository;
import com.cts.CBLOS.service.DisbursementService;
import com.cts.CBLOS.service.DocumentService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private DocumentService documentService;
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
    private DisbursementService disbursementService;
	
	private boolean isAdmin(HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        System.out.println(userEmail);
        
        return userEmail.equalsIgnoreCase("admin@CTS.com");
    }
    @GetMapping("/dashboard") 
    public String showAdminDashboard(Model model, HttpSession session) {
        if (!isAdmin(session)) {
        	String userEmail = (String) session.getAttribute("userEmail");
            System.out.println(userEmail + " is not an admin");
            return "redirect:/login"; 
        }
        return "admindashboard"; 
    }
 

    
  @GetMapping("/review")
  public String reviewAllDocuments(Model model) {
      List<Document> documents = documentService.getAllDocumentsForAdmin();
      model.addAttribute("file", documents);
      return "documents/adminDoc";
  }
  @PostMapping("/validate/{applicationId}")
  
  public String approveOrRejectDocument(@PathVariable Integer applicationId, @RequestParam Boolean isValid) {
	  System.out.println("Received isValid:========================================================================================== " + isValid);
      documentService.validateDocument(applicationId, isValid);
      return "redirect:/admin/review"; 
  }

     
	@PostMapping("/scheduleDisbursement")
	
	public String scheduleDisbursement(@ModelAttribute Disbursement disbursement, Model model) {
		disbursementService.scheduleDisbursement(disbursement);
	    
	    
	    List<Disbursement> disbursements = disbursementService.getAllDisbursements();
	    model.addAttribute("disbursements", disbursements);
	    return "approval/adminReport";
	}

	@GetMapping("/scheduleDisbursement")
	public String showDisbursementForm(Model model) {
	    model.addAttribute("disbursement", new Disbursement());
	    return "approval/scheduleDisbursement"; 
	}

	@GetMapping("/disbursementReport")
	public String generateDisbursementReport(Model model) {
	    List<Disbursement> disbursements = disbursementService.getAllDisbursements();
	    model.addAttribute("disbursements", disbursements);
	    return "approval/adminReport";
	}
	@GetMapping("/view/{documentId}") public void viewPdf(@PathVariable Integer
			documentId, HttpServletResponse response) throws IOException {
				
		  Document document=documentRepository.findById(documentId).get();
		  response.setContentType("application/pdf");
		  response.setHeader("Content-Disposition", "inline; filename=\"" +
		  document.getFilePath() + "\"");
		  response.getOutputStream().write(document.getData());
		  response.getOutputStream().flush(); }
}