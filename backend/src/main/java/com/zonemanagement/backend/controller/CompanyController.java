package com.zonemanagement.backend.controller;

import com.zonemanagement.backend.dto.ApiResponse;
import com.zonemanagement.backend.dto.CompanyDTO;
import com.zonemanagement.backend.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getAllCompanies(
            @RequestParam(required = false) Integer groupId) {

        List<CompanyDTO> companies = groupId != null
                ? companyService.getCompaniesByGroup(groupId)
                : companyService.getAllActiveCompanies();

        return ResponseEntity.ok(ApiResponse.success("Companies fetched successfully", companies));
    }
}
