package com.cts.CBLOS.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.CBLOS.model.CreditEvaluation;
import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.repository.CreditEvaluationRepository;
import com.cts.CBLOS.repository.LoanApplicationRepository;
import com.cts.CBLOS.service.CreditEvaluationService;

@Service
public class CreditEvaluationServiceImpl implements CreditEvaluationService {
	@Autowired
	private CreditEvaluationRepository creditEvaluationRepository;
	
	@Autowired
	private LoanApplicationRepository loanApplicationRepository;
	
	@Override
	public CreditEvaluation evaluateCredit(Integer applicationId) {
		LoanApplication loanApplication=loanApplicationRepository.findById(applicationId)
				.orElseThrow(()->new RuntimeException("Application not found"));
		int creditScore = calculateCreditScore(loanApplication);
        BigDecimal riskScore = calculateRiskScore(loanApplication);

        CreditEvaluation creditEvaluation = new CreditEvaluation();
        creditEvaluation.setLoanApplication(loanApplication);
        creditEvaluation.setCreditScore(creditScore);
        creditEvaluation.setRiskScore(riskScore);
        creditEvaluation.setEvaluationDate(LocalDate.now());

        return creditEvaluationRepository.save(creditEvaluation);
	}
	@Override
    public BigDecimal getRiskScore(Integer applicationId) {
        return creditEvaluationRepository.findById(applicationId)
            .map(CreditEvaluation::getRiskScore)
            .orElseThrow(() -> new RuntimeException("Evaluation not found"));
    }
	private int calculateCreditScore(LoanApplication loanApplication) {
        return loanApplication.getPaymentHistory() * 2 +
               loanApplication.getCreditUtilization() * 3 +
               loanApplication.getLengthOfCreditHistory() * 4 +
               loanApplication.getTypeOfCredit() * 2 +
               loanApplication.getRecentCreditEnquiries() * -1;
    }

    private BigDecimal calculateRiskScore(LoanApplication loanApplication) {
        return BigDecimal.valueOf(100 - loanApplication.getCreditUtilization() * 1.5);
    }
}
