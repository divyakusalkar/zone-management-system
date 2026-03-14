package com.zonemanagement.backend.dto;

public class GroupDTO {

    private Integer groupId;
    private String groupName;
    private Boolean isActive;

    public GroupDTO() {}

    public GroupDTO(Integer groupId, String groupName, Boolean isActive) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.isActive = isActive;
    }

    public Integer getGroupId() { return groupId; }
    public void setGroupId(Integer groupId) { this.groupId = groupId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
