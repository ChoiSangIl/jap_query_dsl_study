package com.example.querydslstudy

import com.example.querydslstudy.entity.Member
import com.example.querydslstudy.entity.QMember
import com.example.querydslstudy.entity.Team
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class QueryDslBasicStudy {

    @Autowired
    lateinit var entityManager: EntityManager

    @BeforeEach
    fun before(){
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

    @Test
    @DisplayName("JPQL 시작")
    fun startJPQL(){
        //member1을 찾아라
        val findMember = entityManager.createQuery("select m from Member m where m.username = :username", Member::class.java)
            .setParameter("username", "member1")

        assertEquals(findMember.singleResult.username, "member1")
    }

    @Test
    @DisplayName("querydsl 시작")
    fun startQuerydsl(){
        val queryFactory = JPAQueryFactory(entityManager)
        val m = QMember.member

        val findMember = queryFactory
            .select(m)
            .from(m)
            .where(m.username.eq("member1"))
            .fetchOne()

        assertEquals(findMember?.username, "member1")
    }
}