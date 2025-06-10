package com.cts.CBLOS.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.cts.CBLOS.model.Approval;
 
@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Integer>{
}