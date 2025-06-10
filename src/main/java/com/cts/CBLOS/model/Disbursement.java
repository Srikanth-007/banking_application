package com.cts.CBLOS.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@Table(name="disbursement")
public class Disbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer disbursementId;

    @ManyToOne
    @JoinColumn(name = "applicationId", nullable = false)
    private LoanApplication loanApplication;

    private BigDecimal disbursedAmount;
    private LocalDate disbursementDate;
    private String repaymentSchedule;
}
