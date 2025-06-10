package com.cts.CBLOS.model;
 
import jakarta.persistence.*;
 
import lombok.Data;
 
import java.time.LocalDate;
 
@Entity
@Data
public class Approval {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer approvalId;
 
    @ManyToOne
    @JoinColumn(name = "applicationId", nullable = false)
    private LoanApplication loanApplication;
    
    private Integer approverId;
    private Integer approvalLevel;
 
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
 
    @Column(columnDefinition = "TEXT")
    private String comments;
 
    private LocalDate approvalDate;
 
 
    public enum ApprovalStatus {
        Pending,
        Approved,
        Rejected
    }
}