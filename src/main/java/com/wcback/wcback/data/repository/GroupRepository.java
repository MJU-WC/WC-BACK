package com.wcback.wcback.data.repository;

import com.wcback.wcback.data.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Object> {
    boolean existsByGroupid(String groupid);
}

