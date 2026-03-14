package com.zonemanagement.backend.controller;

import com.zonemanagement.backend.dto.ApiResponse;
import com.zonemanagement.backend.dto.BrandDTO;
import com.zonemanagement.backend.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandDTO>>> getAllBrands(
            @RequestParam(required = false) Integer companyId,
            @RequestParam(required = false) Integer groupId) {

        List<BrandDTO> brands;
        if (companyId != null) {
            brands = brandService.getBrandsByCompany(companyId);
        } else if (groupId != null) {
            brands = brandService.getBrandsByGroup(groupId);
        } else {
            brands = brandService.getAllActiveBrands();
        }
        return ResponseEntity.ok(ApiResponse.success("Brands fetched successfully", brands));
    }
}
