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

    private String email;

    private String groupID;

    @Column(nullable = false)
    private Date appointment;

    private float lon;

    private float lat;

    private String weather;

    @Column(nullable = false)
    private String address;
}