package com.cts.CBLOS.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.CBLOS.model.CreditEvaluation;
import com.cts.CBLOS.service.CreditEvaluationService;

@Controller
@RequestMapping("/creditEvaluation")
public class CreditEvaluationController{
	@Autowired
	private CreditEvaluationService creditEvaluationService;
	
	@PostMapping("/{applicationId}/evaluate")
    public ResponseEntity<CreditEvaluation> evaluateCredit(@PathVariable Integer applicationId) {
        return ResponseEntity.ok(creditEvaluationService.evaluateCredit(applicationId));
    }

    @GetMapping("/{applicationId}/risk-score")
    public ResponseEntity<BigDecimal> getRiskScore(@PathVariable Integer applicationId) {
        return ResponseEntity.ok(creditEvaluationService.getRiskScore(applicationId));
    }
}
