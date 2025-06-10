package com.cts.CBLOS.service;
 
public interface ApprovalService {
 
	String processLoanApproval(Integer applicationId);
 
	String getApplicationStatus(Integer applicationId);
 
	
}