package com.cts.CBLOS.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.CBLOS.model.Document;

import java.util.List;
import java.util.Optional;
public interface DocumentRepository extends JpaRepository<Document, Integer> {
	
	
	 List<Document> findByLoanApplication_ApplicationId(Integer applicationId);
	 
	 @Query("SELECT d FROM Document d ORDER BY d.loanApplication.applicationId ASC")
	  List<Document> findAllDocuments();
		 
}
