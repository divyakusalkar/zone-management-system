package com.zonemanagement.backend.repository;

import com.zonemanagement.backend.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {

    @Query("""
            SELECT z FROM Zone z
            JOIN FETCH z.brand b
            JOIN FETCH b.company c
            JOIN FETCH c.group g
            WHERE (:brandId    IS NULL OR b.brandId    = :brandId)
              AND (:companyId  IS NULL OR c.companyId  = :companyId)
              AND (:groupId    IS NULL OR g.groupId    = :groupId)
              AND (:search     IS NULL OR LOWER(z.zoneName) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Zone> findAllWithFilters(
            @Param("brandId")   Integer brandId,
            @Param("companyId") Integer companyId,
            @Param("groupId")   Integer groupId,
            @Param("search")    String search,
            Pageable pageable
    );
}
