
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
    public Group loopMembers(String groupName, List<String> members) {
        for (String member : members) {createGroup(member,groupName);}
        return findById(groupName);
    }

    // 한 멤버씩 그룹에 넣기
    public void createGroup(String member,String groupName) {
        Group group = new Group();
        group.setGroupId(groupName);
        group.setEmail(member);
        groupRepository.save(group);
    }
    
    // 그룹Id로 그룹 찾기
    public Group findById(String groupName) {
        return groupRepository.findById(groupName).get();
    }

    // 그룹Id 중복체크
    public boolean checkGroup(String groupName) {
        return groupRepository.existsByGroupId(groupName);
    }

    // 그룹 삭제
    public void deleteById(String groupName) {
        groupRepository.deleteById(groupName);
    }

}

