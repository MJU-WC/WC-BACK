package com.wcback.wcback.data.entity;

import lombok.*;
import org.hibernate.type.BigIntegerType;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "client")
public class User {

    @Id
    @Column(unique = true, length = 30, nullable = false)
    private String email;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false)
    private float lat;

    @Column(nullable = false)
    private float lon;

    @Column
    private String pwd;

    @Column
    private String profile_image;

    @Column
    private String token = "";

    @Column(nullable = false)
    private boolean iskakao;

}
