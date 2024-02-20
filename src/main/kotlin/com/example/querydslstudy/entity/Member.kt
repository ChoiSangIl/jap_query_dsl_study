package com.example.querydslstudy.entity

import jakarta.persistence.*

@Entity
data class Member (
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    var id: Long = 0,
    val username: String,
    val age: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    val team: Team?
)