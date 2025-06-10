package com.cts.CBLOS.service;

import java.util.List;

import com.cts.CBLOS.model.Disbursement;

public interface DisbursementService {
	List<Disbursement> getDisbursementsByApplicationId(Integer applicationId);
	List<Disbursement> getAllDisbursements();
	Disbursement scheduleDisbursement(Disbursement disbursement);
}
