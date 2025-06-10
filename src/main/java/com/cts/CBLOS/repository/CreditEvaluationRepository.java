package com.cts.CBLOS.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.CBLOS.model.CreditEvaluation;
import com.cts.CBLOS.model.LoanApplication;
@Repository
public interface CreditEvaluationRepository extends JpaRepository<CreditEvaluation, Integer>{
 
	CreditEvaluation findByLoanApplication(LoanApplication loanApp);
}