package com.example.querydslstudy

import com.example.querydslstudy.entity.Member
import com.example.querydslstudy.entity.Team
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class QueryDslBasicStudy {

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testEntity(){
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        entityManager.persist(teamA)
        entityManager.persist(teamB)

        val member1 = Member(username = "member1", age = 10, team = teamA)
        val member2 = Member(username = "member2", age = 20, team = teamA)
        val member3 = Member(username = "member3", age = 30, team = teamB)
        val member4 = Member(username = "member4", age = 40, team = teamB)

        entityManager.persist(member1)
        entityManager.persist(member2)
        entityManager.persist(member3)
        entityManager.persist(member4)

        entityManager.flush()
        entityManager.clear()
    }
}