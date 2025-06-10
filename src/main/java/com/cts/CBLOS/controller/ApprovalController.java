package com.cts.CBLOS.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.cts.CBLOS.service.ApprovalService;
import com.cts.CBLOS.service.ApprovalServiceImpl;
 
@RestController
@RequestMapping("/approval")
public class ApprovalController {
 
    @Autowired 
    private ApprovalService approvalService;
 
    @PostMapping("/processApproval/{applicationId}")
    public ResponseEntity<String> processApproval(@PathVariable int applicationId) {
        try {
            String result = approvalService.processLoanApproval(applicationId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error processing approval: " + e.getMessage());
        }
    }
 
    @GetMapping("/applicationStatus/{applicationId}")
    public ResponseEntity<String> getApplicationStatus(@PathVariable int applicationId) {
        try {
            String status = approvalService.getApplicationStatus(applicationId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving application status: " + e.getMessage());
        }
    }
}