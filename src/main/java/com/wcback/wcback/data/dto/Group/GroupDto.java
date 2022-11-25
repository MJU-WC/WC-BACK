
package com.wcback.wcback.data.dto.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

public class GroupDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupRegisterDto {
        String groupid;
        String[] members;
        String leaderName;
    }
}

