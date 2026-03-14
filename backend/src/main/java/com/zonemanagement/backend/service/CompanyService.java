package com.zonemanagement.backend.service;

import com.zonemanagement.backend.dto.CompanyDTO;
import com.zonemanagement.backend.entity.Company;
import com.zonemanagement.backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyDTO> getAllActiveCompanies() {
        return companyRepository.findByIsActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CompanyDTO> getCompaniesByGroup(Integer groupId) {
        return companyRepository.findByGroup_GroupIdAndIsActiveTrue(groupId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private CompanyDTO toDTO(Company c) {
        return new CompanyDTO(
                c.getCompanyId(),
                c.getCompanyName(),
                c.getGroup().getGroupId(),
                c.getGroup().getGroupName(),
                c.getIsActive()
        );
    }
}
