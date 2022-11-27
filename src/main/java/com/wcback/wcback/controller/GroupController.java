package com.wcback.wcback.controller;

import com.wcback.wcback.data.dto.Group.GroupDto;
import com.wcback.wcback.data.entity.Group;
import com.wcback.wcback.exception.user.AlreadyExistException;
import com.wcback.wcback.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Transactional
    @GetMapping
    public ResponseEntity<Object> getGroup(@RequestParam String groupid) {
        return ResponseEntity.ok().body(groupService.findById(groupid));
    }

    // 그룹 생성
    @Transactional
    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody GroupDto.GroupRegisterDto groupRegisterDto) throws AlreadyExistException {
        String groupid = groupRegisterDto.getGroupid();
        String[] members = groupRegisterDto.getMembers();
        String leaderName = groupRegisterDto.getLeaderName();
        if (groupService.checkGroup(groupid)) {
            throw new AlreadyExistException("이미 존재하는 그룹명입니다.");
        }
        return ResponseEntity.ok().body(groupService.loopMembers(groupid, members, leaderName));
    }

    // 그룹 삭제
    @Transactional
    @DeleteMapping
    public ResponseEntity<Object> deleteGroup(@RequestParam String groupName) {
        groupService.deleteById(groupName);
        return ResponseEntity.ok().body("그룹 삭제 완료");
    }

}

