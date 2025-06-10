package com.cts.CBLOS.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "loan_applications")
@Data
public class LoanApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer applicationId;
	private String companyName;
	private String loanType;
	private Double loanAmount;
	@Enumerated(EnumType.STRING)
	private LoanApplicationStatus status;
	private LocalDate submissionDate;
	private Integer paymentHistory;
	private Integer creditUtilization;
	private Integer lengthOfCreditHistory;
	private Integer TypeOfCredit;
	private Integer RecentCreditEnquiries;
	
	@OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
	
	public enum LoanApplicationStatus {
        Pending,
        Approved,
        Rejected
    }
	
	

}
