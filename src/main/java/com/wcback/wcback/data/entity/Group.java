package com.wcback.wcback.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "groupId")
public class Group {
    @Id
    @Column(unique = true, nullable = false)
    private String groupId;

    @Column(nullable = false)
    private String email;
}

