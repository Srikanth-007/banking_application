package com.cts.CBLOS.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private String email;
    private String password;
 
    @Enumerated(EnumType.STRING)
    private UserRole role; 
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LoanApplication loanApplication;
    
    public enum UserRole {

    	ADMIN,
    	USER
    }

}

 