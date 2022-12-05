package com.wcback.wcback.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Schedule")
public class Schedule {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column(nullable = false)
    private String scheduleName;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String groupID;

    @Column(nullable = false)
    private Date appointment;

    @Column(nullable = false)
    private float lon;

    @Column(nullable = false)
    private float lat;

    @Column(nullable = true)
    private String weather;
}