package com.wcback.wcback.data.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @Column(unique = true, length = 30, nullable = false)
    private String email;

    @Column(length = 15, nullable = false)
    private String userName;

    @Column(nullable = false)
    private Long lat;

    @Column(nullable = false)
    private Long lng;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String profile_image;

    @Column
    private String accessToken = "";

}
