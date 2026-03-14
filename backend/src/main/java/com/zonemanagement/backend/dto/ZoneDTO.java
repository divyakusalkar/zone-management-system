package com.zonemanagement.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ZoneDTO {

    // ---- Response DTO (returned to client) ----
    public static class Response {
        private Integer zoneId;
        private String zoneName;
        private Integer brandId;
        private String brandName;
        private Integer companyId;
        private String companyName;
        private Integer groupId;
        private String groupName;
        private Boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response() {}

        public Response(Integer zoneId, String zoneName, Integer brandId, String brandName,
                        Integer companyId, String companyName, Integer groupId, String groupName,
                        Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.zoneId = zoneId;
            this.zoneName = zoneName;
            this.brandId = brandId;
            this.brandName = brandName;
            this.companyId = companyId;
            this.companyName = companyName;
            this.groupId = groupId;
            this.groupName = groupName;
            this.isActive = isActive;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public Integer getZoneId() { return zoneId; }
        public void setZoneId(Integer zoneId) { this.zoneId = zoneId; }

        public String getZoneName() { return zoneName; }
        public void setZoneName(String zoneName) { this.zoneName = zoneName; }

        public Integer getBrandId() { return brandId; }
        public void setBrandId(Integer brandId) { this.brandId = brandId; }

        public String getBrandName() { return brandName; }
        public void setBrandName(String brandName) { this.brandName = brandName; }

        public Integer getCompanyId() { return companyId; }
        public void setCompanyId(Integer companyId) { this.companyId = companyId; }

        public String getCompanyName() { return companyName; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }

        public Integer getGroupId() { return groupId; }
        public void setGroupId(Integer groupId) { this.groupId = groupId; }

        public String getGroupName() { return groupName; }
        public void setGroupName(String groupName) { this.groupName = groupName; }

        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }

    // ---- Request DTO (received from client on create/update) ----
    public static class Request {

        @NotBlank(message = "Zone name is required")
        @Size(max = 50, message = "Zone name must not exceed 50 characters")
        private String zoneName;

        @NotNull(message = "Brand is required")
        private Integer brandId;

        public Request() {}

        public Request(String zoneName, Integer brandId) {
            this.zoneName = zoneName;
            this.brandId = brandId;
        }

        public String getZoneName() { return zoneName; }
        public void setZoneName(String zoneName) { this.zoneName = zoneName; }

        public Integer getBrandId() { return brandId; }
        public void setBrandId(Integer brandId) { this.brandId = brandId; }
    }
}
