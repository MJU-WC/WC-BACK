package com.wcback.wcback.service;

import com.wcback.wcback.data.entity.Group;
import com.wcback.wcback.data.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {
    GroupRepository groupRepository;

    // 멤버마다 그룹에 넣기
    public List<Group> loopMembers(String groupName, String[] members, String leaderName) {
        for (String member : members) {createGroup(member,groupName, leaderName);}
        return findById(groupName);
    }

    // 멤버를 그룹에 넣기
    public void createGroup(String member,String groupName, String leaderName) {
        Group group = new Group();
        group.setGroupid(groupName);
        group.setEmail(member);
        if (member.equals(leaderName)) {
            group.setLeader(true);
        }
        groupRepository.save(group);
    }
    
    // 그룹Id로 그룹 찾기
    public List<Group> findById(String groupName) {
        return groupRepository.findByGroupid(groupName);
    }

    // 그룹Id 중복체크
    public boolean checkGroup(String groupName) {
        return groupRepository.existsByGroupid(groupName);
    }

    // 그룹 삭제
    public void deleteById(String groupName) {
        groupRepository.deleteByGroupid(groupName);
    }

}

