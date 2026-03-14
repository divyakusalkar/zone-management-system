package com.zonemanagement.backend.service;

import com.zonemanagement.backend.dto.PagedResponse;
import com.zonemanagement.backend.dto.ZoneDTO;
import com.zonemanagement.backend.entity.Brand;
import com.zonemanagement.backend.entity.Zone;
import com.zonemanagement.backend.exception.ResourceNotFoundException;
import com.zonemanagement.backend.repository.BrandRepository;
import com.zonemanagement.backend.repository.ZoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final BrandRepository brandRepository;

    public ZoneService(ZoneRepository zoneRepository, BrandRepository brandRepository) {
        this.zoneRepository = zoneRepository;
        this.brandRepository = brandRepository;
    }

    // ----------------------------------------------------------------
    // GET all zones with filters and pagination
    // ----------------------------------------------------------------
    public PagedResponse<ZoneDTO.Response> getZones(
            Integer brandId,
            Integer companyId,
            Integer groupId,
            String search,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Zone> zonePage = zoneRepository.findAllWithFilters(
                brandId, companyId, groupId,
                (search == null || search.isBlank()) ? null : search,
                pageable
        );

        List<ZoneDTO.Response> content = zonePage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                zonePage.getNumber(),
                zonePage.getSize(),
                zonePage.getTotalElements(),
                zonePage.getTotalPages(),
                zonePage.isLast()
        );
    }

    // ----------------------------------------------------------------
    // GET single zone
    // ----------------------------------------------------------------
    public ZoneDTO.Response getZoneById(Integer id) {
        Zone zone = findZoneOrThrow(id);
        return toDTO(zone);
    }

    // ----------------------------------------------------------------
    // CREATE zone
    // ----------------------------------------------------------------
    @Transactional
    public ZoneDTO.Response createZone(ZoneDTO.Request request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));

        Zone zone = new Zone();
        zone.setZoneName(request.getZoneName().trim());
        zone.setBrand(brand);
        zone.setIsActive(true);

        Zone saved = zoneRepository.save(zone);
        return toDTO(saved);
    }

    // ----------------------------------------------------------------
    // UPDATE zone
    // ----------------------------------------------------------------
    @Transactional
    public ZoneDTO.Response updateZone(Integer id, ZoneDTO.Request request) {
        Zone zone = findZoneOrThrow(id);

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));

        zone.setZoneName(request.getZoneName().trim());
        zone.setBrand(brand);

        Zone updated = zoneRepository.save(zone);
        return toDTO(updated);
    }

    // ----------------------------------------------------------------
    // SOFT DELETE zone
    // ----------------------------------------------------------------
    @Transactional
    public void deleteZone(Integer id) {
        Zone zone = findZoneOrThrow(id);
        zone.setIsActive(false);
        zoneRepository.save(zone);
    }

    // ----------------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------------
    private Zone findZoneOrThrow(Integer id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone", "id", id));
    }

    private ZoneDTO.Response toDTO(Zone z) {
        Brand brand = z.getBrand();
        return new ZoneDTO.Response(
                z.getZoneId(),
                z.getZoneName(),
                brand.getBrandId(),
                brand.getBrandName(),
                brand.getCompany().getCompanyId(),
                brand.getCompany().getCompanyName(),
                brand.getCompany().getGroup().getGroupId(),
                brand.getCompany().getGroup().getGroupName(),
                z.getIsActive(),
                z.getCreatedAt(),
                z.getUpdatedAt()
        );
    }
}
