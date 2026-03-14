package com.zonemanagement.backend.controller;

import com.zonemanagement.backend.dto.ApiResponse;
import com.zonemanagement.backend.dto.PagedResponse;
import com.zonemanagement.backend.dto.ZoneDTO;
import com.zonemanagement.backend.service.ZoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    // GET /api/zones  — with optional filters & pagination
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ZoneDTO.Response>>> getAllZones(
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer companyId,
            @RequestParam(required = false) Integer groupId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "zoneId")  String sortBy,
            @RequestParam(defaultValue = "asc")     String sortDir) {

        PagedResponse<ZoneDTO.Response> response = zoneService.getZones(
                brandId, companyId, groupId, search, page, size, sortBy, sortDir);

        return ResponseEntity.ok(ApiResponse.success("Zones fetched successfully", response));
    }

    // GET /api/zones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ZoneDTO.Response>> getZoneById(@PathVariable Integer id) {
        ZoneDTO.Response zone = zoneService.getZoneById(id);
        return ResponseEntity.ok(ApiResponse.success("Zone fetched successfully", zone));
    }

    // POST /api/zones
    @PostMapping
    public ResponseEntity<ApiResponse<ZoneDTO.Response>> createZone(
            @Valid @RequestBody ZoneDTO.Request request) {
        ZoneDTO.Response created = zoneService.createZone(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Zone created successfully", created));
    }

    // PUT /api/zones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ZoneDTO.Response>> updateZone(
            @PathVariable Integer id,
            @Valid @RequestBody ZoneDTO.Request request) {
        ZoneDTO.Response updated = zoneService.updateZone(id, request);
        return ResponseEntity.ok(ApiResponse.success("Zone updated successfully", updated));
    }

    // DELETE /api/zones/{id}  — soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteZone(@PathVariable Integer id) {
        zoneService.deleteZone(id);
        return ResponseEntity.ok(ApiResponse.success("Zone deleted successfully", null));
    }
}
