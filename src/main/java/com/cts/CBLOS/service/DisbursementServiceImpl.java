package com.cts.CBLOS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.CBLOS.model.Disbursement;
import com.cts.CBLOS.repository.DisbursementRepository;
@Service
public class DisbursementServiceImpl implements DisbursementService {
	@Autowired
    private DisbursementRepository disbursementRepository;
	
	@Override
    public List<Disbursement> getDisbursementsByApplicationId(Integer applicationId) {
        return disbursementRepository.findByLoanApplicationApplicationId(applicationId);
    }
	
	@Override
	public Disbursement scheduleDisbursement(Disbursement disbursement) {
        return disbursementRepository.save(disbursement); 
    }

	@Override
	public List<Disbursement> getAllDisbursements() {
        return disbursementRepository.findAll();
    }
}
