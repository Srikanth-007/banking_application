package com.cts.CBLOS.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.model.LoanApplication.LoanApplicationStatus;
import com.cts.CBLOS.repository.LoanApplicationRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {
	
	@Autowired
	private LoanApplicationRepository loanApplicationRepository;

    @Override
    @Transactional
    public LoanApplication submitApplication(LoanApplication loanApplication) {
        loanApplication.setStatus(LoanApplicationStatus.Pending);
        loanApplication.setSubmissionDate(LocalDate.now());
        return loanApplicationRepository.save(loanApplication);
    }

    @Override
    public LoanApplication trackApplicationStatus(Integer applicationId) {
        return loanApplicationRepository.findById(applicationId)
                .orElse(null); 
    }
    
    @Override
    public Optional<LoanApplication> getApplicationById(Integer applicationId) {
        return loanApplicationRepository.findById(applicationId);
    }

}
