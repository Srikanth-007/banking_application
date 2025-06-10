package com.cts.CBLOS.service;

import java.math.BigDecimal;

import com.cts.CBLOS.model.CreditEvaluation;

public interface CreditEvaluationService {
	CreditEvaluation evaluateCredit(Integer applicationId);
	BigDecimal getRiskScore(Integer applicationId);
}
