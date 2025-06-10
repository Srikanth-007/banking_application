package com.cts.CBLOS.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="CreditEvaluation")
@Data
public class CreditEvaluation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer evaluationId;
	
	@ManyToOne
	@JoinColumn(name="applicationId", nullable=false)
	private LoanApplication loanApplication;
	
	private BigDecimal riskScore;
	private Integer creditScore;
	private LocalDate evaluationDate;
	
	
}
