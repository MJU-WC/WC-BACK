package com.wcback.wcback.data.entity;

import lombok.*;

import javax.persistence.*;

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
    private Long lon;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String profile_image;

    @Column
    private String accessToken = "";

}
