package com.zonemanagement.backend.service;

import com.zonemanagement.backend.dto.GroupDTO;
import com.zonemanagement.backend.entity.Group;
import com.zonemanagement.backend.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupDTO> getAllActiveGroups() {
        return groupRepository.findByIsActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private GroupDTO toDTO(Group g) {
        return new GroupDTO(g.getGroupId(), g.getGroupName(), g.getIsActive());
    }
}
