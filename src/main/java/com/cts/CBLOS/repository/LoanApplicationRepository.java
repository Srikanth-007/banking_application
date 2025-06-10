package com.cts.CBLOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.model.User;
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer> {
	LoanApplication findByUser(User user);
}
