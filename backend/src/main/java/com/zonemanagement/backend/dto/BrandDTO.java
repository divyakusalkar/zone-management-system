package com.zonemanagement.backend.dto;

public class BrandDTO {

    private Integer brandId;
    private String brandName;
    private Integer companyId;
    private String companyName;
    private Integer groupId;
    private String groupName;
    private Boolean isActive;

    public BrandDTO() {}

    public BrandDTO(Integer brandId, String brandName, Integer companyId, String companyName,
                    Integer groupId, String groupName, Boolean isActive) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.groupId = groupId;
        this.groupName = groupName;
        this.isActive = isActive;
    }

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
}
