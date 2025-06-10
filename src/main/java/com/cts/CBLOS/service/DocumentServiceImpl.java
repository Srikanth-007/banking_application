package com.cts.CBLOS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.CBLOS.model.Document;
import com.cts.CBLOS.model.LoanApplication;
import com.cts.CBLOS.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private ApprovalService approvalService;

	@Override
	public Document uploadDocument(Document document) {
		if (document.getLoanApplication() == null) {
			throw new IllegalArgumentException("Application ID is required to upload a document.");
		}
		return documentRepository.save(document);
	}

	@Override
	public List<Document> validateDocument(Integer applicationId, Boolean isValid) {
		List<Document> documents = documentRepository.findByLoanApplication_ApplicationId(applicationId);

		if (!documents.isEmpty()) {
			for (Document document : documents) {
				System.out.println("Updating document ID: " + document.getDocumentId() + " to isValid=" + isValid);
				document.setIsValid(isValid);
				approvalService.processLoanApproval(applicationId);
			}
			return documentRepository.saveAll(documents); // Save and return updated list }
		}

		return Collections.emptyList();
//        Optional<Document> doc = documentRepository.findById(documentId);
//        if (doc.isPresent()) {
//        	Document document = doc.get();
//        	document.setIsValid(isValid);
////        	LoanApplication loanApplication = document.getLoanApplication();
////        	approvalService.processLoanApproval(loanApplication);
//        	return documentRepository.save(document);
//
//        }  
//        return null;
	}

	@Override
	public List<Document> getDocumentsByApplicationId(Integer applicationId) {
		return documentRepository.findByLoanApplication_ApplicationId(applicationId);
	}

	@Override
	public List<Document> getAllDocumentsForAdmin() {
		return documentRepository.findAllDocuments();
	}

}
