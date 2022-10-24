package com.wcback.wcback.data.entity;

import lombok.*;
import org.springframework.stereotype.Component;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String userName;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String profile_image;

    @Column(nullable = false)
    private String address;

    @Column
    private String accessToken = "";
}
