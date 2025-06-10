package com.cts.CBLOS.service;

import java.util.List;
import java.util.Optional;

import com.cts.CBLOS.model.Document;
import com.cts.CBLOS.model.LoanApplication;
public interface DocumentService {
	Document uploadDocument(Document document);
    List<Document> validateDocument(Integer documentId, Boolean isValid);
    List<Document> getDocumentsByApplicationId(Integer applicationId);
    List<Document> getAllDocumentsForAdmin();

}
