package com.zonemanagement.backend.controller;

import com.zonemanagement.backend.dto.ApiResponse;
import com.zonemanagement.backend.dto.GroupDTO;
import com.zonemanagement.backend.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupDTO>>> getAllGroups() {
        List<GroupDTO> groups = groupService.getAllActiveGroups();
        return ResponseEntity.ok(ApiResponse.success("Groups fetched successfully", groups));
    }
}
