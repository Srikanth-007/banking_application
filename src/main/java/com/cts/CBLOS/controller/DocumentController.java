package com.cts.CBLOS.controller;
 
import com.cts.CBLOS.model.Document;
import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.repository.DocumentRepository;
import com.cts.CBLOS.service.DocumentService;
import com.cts.CBLOS.service.LoanApplicationService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Controller;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
@Controller
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentRepository documentRepository;
 
    @Autowired
    private LoanApplicationService loanApplicationService; 
    private static final String UPLOAD_DIR = "uploads/";
 
    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("applicationId") Integer applicationId) {
    	
    	Optional<LoanApplication> loanApplicationOpt = loanApplicationService.getApplicationById(applicationId);
    	
        if(file.isEmpty()) {
        	return "File upload is required";
        }
        try {
        	String documentType = "";
            if (file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")) {
                documentType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
            }
 
            List<String> validFormats = Arrays.asList("pdf", "docx", "jpeg", "png");
            if (!validFormats.contains(documentType)) {
                return "Invalid document format";
            }
            File uploadDir = new File(UPLOAD_DIR);
        	if (!uploadDir.exists()) {
        	    uploadDir.mkdirs();
        	}

        	String filePath=UPLOAD_DIR+file.getOriginalFilename();
        	System.out.println(file.getOriginalFilename());
        	File destFile=new File(filePath);
        	System.out.println("Exception");
        	file.transferTo(destFile.toPath());
        	LoanApplication loanApplication = loanApplicationOpt.get();
        	Document document=new Document();
        	document.setLoanApplication(loanApplication);
        	document.setDocumentType(documentType);
        	document.setFilePath(filePath);
        	document.setUploadDate(LocalDate.now());
        	document.setIsValid(null);
        	document.setData(file.getBytes());
        	documentService.uploadDocument(document);
        	return "redirect:/home";
        }catch(IOException e){
        	return "File Upload failed";
        }

 
    }
 

    @GetMapping("/upload")
    public String showUploadForm(@RequestParam("applicationId") Integer applicationId , Model model) {

    	model.addAttribute("applicationId", applicationId);
        model.addAttribute("document", new Document());
        return "documents/upload"; 
    }
    @GetMapping("/viewDocument")
    public String viewUploadedDocuments( Model model,HttpSession session) {
    	Integer applicationId = (Integer) session.getAttribute("applicationId");

        if (applicationId == null) {
            model.addAttribute("errorMessage", "Application ID not found in session!");
            return "loanApplication/error";
        }
    	List<Document> documents = documentService.getDocumentsByApplicationId(applicationId);
        System.out.println("Documents retrieved for applicationId " + applicationId + ": " + documents.size());
        model.addAttribute("file", documents);
        System.out.println("hhhh");
        return "documents/viewDoc"; 
    }

	  @GetMapping("/view/{documentId}") public void viewPdf(@PathVariable Integer
		documentId, HttpServletResponse response) throws IOException {
			
	  Document document=documentRepository.findById(documentId).get();
	  response.setContentType("application/pdf");
	  response.setHeader("Content-Disposition", "inline; filename=\"" +
	  document.getFilePath() + "\"");
	  response.getOutputStream().write(document.getData());
	  response.getOutputStream().flush(); 
	  }
	 
}
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
//    @GetMapping("/documents/viewDocument/uploads/{filename}")
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//        try {
//            Path filePath = Paths.get("uploads").resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (!((UrlResource) resource).exists()) { // Explicitly cast to UrlResource for exists() method
//                return ResponseEntity.notFound().build();
//            }
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename)
//                    .body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @GetMapping("/review")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String reviewAllDocuments(Model model) {
//        List<Document> documents = documentService.getAllDocumentsForAdmin();
//        model.addAttribute("file", documents);
//        return "adminDoc";
//    }
//    @PostMapping("/validate/{documentId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String approveOrRejectDocument(@PathVariable Integer documentId, @RequestParam Boolean isValid) {
//        documentService.validateDocument(documentId, isValid);
//        return "redirect:/home"; // Redirect to homepage after action
//    }
