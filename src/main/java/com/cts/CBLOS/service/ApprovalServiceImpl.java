package com.cts.CBLOS.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.model.LoanApplication.LoanApplicationStatus;
import com.cts.CBLOS.model.Approval;
import com.cts.CBLOS.model.Approval.ApprovalStatus;
import com.cts.CBLOS.model.CreditEvaluation;
import com.cts.CBLOS.model.Document;
import com.cts.CBLOS.repository.ApprovalRepository;
import com.cts.CBLOS.repository.CreditEvaluationRepository;
import com.cts.CBLOS.repository.DocumentRepository;
import com.cts.CBLOS.repository.LoanApplicationRepository;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
 
@Service
public class ApprovalServiceImpl implements ApprovalService {
 
	@Autowired
	private LoanApplicationRepository loanAppRepo;
	@Autowired
	private CreditEvaluationRepository creditEvalRepo;
	@Autowired
	private DocumentRepository docRepo;
	@Autowired
	private ApprovalRepository approvalRepo;
 
	@SuppressWarnings("unused")
	@Override
	public String processLoanApproval(Integer applicationId) {
		Integer managerId=1;
		Integer seniorManagerId=2;
		LoanApplication loanApp = loanAppRepo.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found"));
 
		if (loanApp.getStatus().equals("Rejected") || loanApp.getStatus().equals("Approved")) {
			return "Application already processed.";
		}
		CreditEvaluation creditEval = creditEvalRepo.findByLoanApplication(loanApp);
		System.out.println("===================================================="+creditEval.getCreditScore());
		List<Document> documents = docRepo.findByLoanApplication_ApplicationId(applicationId);

		if (creditEval == null || creditEval.getCreditScore() < 650
				|| creditEval.getRiskScore().compareTo(new BigDecimal("70")) > 0) {
			saveApproval(loanApp, managerId, 1, "Rejected", "Credit evaluation failed");
			loanApp.setStatus(LoanApplicationStatus.Rejected);
			loanAppRepo.save(loanApp);
			return "Loan Rejected: Credit evaluation failed.";
		}
 
		if (documents.isEmpty() || documents.stream().anyMatch(doc -> !doc.getIsValid()))

		{
			saveApproval(loanApp, managerId, 1, "Rejected", "Document verification failed");
			loanApp.setStatus(LoanApplicationStatus.Rejected);
			loanAppRepo.save(loanApp);
			return "Loan Rejected: Document verification failed.";
		}
 
		
		Double loanAmount = loanApp.getLoanAmount();
 
		if (loanAmount > 0 && loanAmount <= 1000000)  {
			saveApproval(loanApp, managerId, 1, "Approved", "Approved at Level 1");
		} else if (loanAmount > 1000000 && loanAmount <= 10000000){ 
			saveApproval(loanApp, seniorManagerId, 2, "Approved", "Approved at Level 2");
		} else {
			saveApproval(loanApp, managerId, 1, "Rejected", "Loan amount exceding the limit");
			loanApp.setStatus(LoanApplicationStatus.Rejected);
			loanAppRepo.save(loanApp);
			return "Loan Rejected: Invalid loan amount.";	
		}
		
		loanApp.setStatus(LoanApplicationStatus.Approved);
		loanAppRepo.save(loanApp);
		return "Loan Approved successfully.";

	}
 
 
	private void saveApproval(LoanApplication loanApp, int approverId, int approvalLevel, String status, String comments) {
		Approval approval = new Approval();
		approval.setLoanApplication(loanApp);
		approval.setApproverId(approverId);
		approval.setApprovalLevel(approvalLevel);
		approval.setComments(comments);
		approval.setApprovalDate(LocalDate.now());

 
		if (status.equalsIgnoreCase("Approved")) {
			approval.setApprovalStatus(Approval.ApprovalStatus.Approved);
		} else if (status.equalsIgnoreCase("Rejected")) {
			approval.setApprovalStatus(Approval.ApprovalStatus.Rejected);
		} else if (status.equalsIgnoreCase("Pending")) {
			approval.setApprovalStatus(Approval.ApprovalStatus.Pending);
		}
 
		
		approvalRepo.save(approval);
	}
	@Override
	public String getApplicationStatus(Integer applicationId) {
		LoanApplication loanApp = loanAppRepo.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found"));
		return "Application ID: " + applicationId + " | Status: " + loanApp.getStatus();
	}
 
 
	
}