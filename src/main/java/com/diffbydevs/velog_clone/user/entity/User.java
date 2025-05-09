package com.diffbydevs.velog_clone.user.entity;

import com.diffbydevs.velog_clone.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(length = 20)
    private String userId;

    @NotNull
    private String blogName;

    @Column(length = 300)
    private String profileIntro;

    @Column(length = 300)
    private String blogIntro;
}
