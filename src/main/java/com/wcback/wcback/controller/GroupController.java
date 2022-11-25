package com.wcback.wcback.controller;

import com.wcback.wcback.exception.user.AlreadyExistException;
import com.wcback.wcback.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    // 그룹 생성
    @PostMapping("/createGroup")
    public ResponseEntity<Object> createGroup(@RequestBody String groupName, @RequestBody List<String> members, @RequestBody String leaderName) throws AlreadyExistException {
        if (groupService.checkGroup(groupName)) {
            throw new AlreadyExistException("이미 존재하는 그룹명입니다.");
        }
        return ResponseEntity.ok().body(groupService.loopMembers(groupName, members, leaderName));
    }

    // 그룹 삭제
    @DeleteMapping("/deleteGroup")
    public ResponseEntity<Object> deleteGroup(@RequestParam String groupName) {
        groupService.deleteById(groupName);
        return ResponseEntity.ok().body("그룹 삭제 완료");
    }


}

