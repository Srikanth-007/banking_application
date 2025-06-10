package com.cts.CBLOS.model;

import jakarta.persistence.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "Document")
@Data
public class Document {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentId;

	@OneToOne
    @JoinColumn(name = "applicationId", nullable = false)
	@JsonIgnore
    private LoanApplication loanApplication;
    private String documentType;
    private String filePath;
    private LocalDate uploadDate;

    private Boolean isValid;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;
	


}
