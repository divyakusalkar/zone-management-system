package com.zonemanagement.backend.repository;

import com.zonemanagement.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findByIsActiveTrue();
    List<Company> findByGroup_GroupIdAndIsActiveTrue(Integer groupId);
}
