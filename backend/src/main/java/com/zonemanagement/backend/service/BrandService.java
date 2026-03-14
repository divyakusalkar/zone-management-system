package com.zonemanagement.backend.service;

import com.zonemanagement.backend.dto.BrandDTO;
import com.zonemanagement.backend.entity.Brand;
import com.zonemanagement.backend.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandDTO> getAllActiveBrands() {
        return brandRepository.findByIsActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BrandDTO> getBrandsByCompany(Integer companyId) {
        return brandRepository.findByCompany_CompanyIdAndIsActiveTrue(companyId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BrandDTO> getBrandsByGroup(Integer groupId) {
        return brandRepository.findByCompany_Group_GroupIdAndIsActiveTrue(groupId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private BrandDTO toDTO(Brand b) {
        return new BrandDTO(
                b.getBrandId(),
                b.getBrandName(),
                b.getCompany().getCompanyId(),
                b.getCompany().getCompanyName(),
                b.getCompany().getGroup().getGroupId(),
                b.getCompany().getGroup().getGroupName(),
                b.getIsActive()
        );
    }
}
