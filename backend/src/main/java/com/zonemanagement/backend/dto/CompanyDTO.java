package com.zonemanagement.backend.dto;

public class CompanyDTO {

    private Integer companyId;
    private String companyName;
    private Integer groupId;
    private String groupName;
    private Boolean isActive;

    public CompanyDTO() {}

    public CompanyDTO(Integer companyId, String companyName, Integer groupId, String groupName, Boolean isActive) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.groupId = groupId;
        this.groupName = groupName;
        this.isActive = isActive;
    }

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
