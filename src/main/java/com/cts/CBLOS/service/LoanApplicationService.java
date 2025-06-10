package com.cts.CBLOS.service;

import java.util.Optional;

import com.cts.CBLOS.model.LoanApplication;

public interface LoanApplicationService {
	
	 	LoanApplication submitApplication(LoanApplication loanApplication);
	    LoanApplication trackApplicationStatus(Integer applicationId);
	    Optional<LoanApplication> getApplicationById(Integer applicationId);
}
