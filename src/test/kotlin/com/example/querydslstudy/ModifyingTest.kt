package com.example.querydslstudy

import com.example.querydslstudy.entity.ReminderRepository
import com.example.querydslstudy.entity.ReminderTest
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
@Transactional
class ModifyingTest {

    @Autowired
    lateinit var repository: ReminderRepository


    @Autowired
    lateinit var entityManager: EntityManager

    @BeforeEach
    fun init(){
        val list: MutableList<ReminderTest> = mutableListOf()
        for (i in 1..90000) {
            list.add(
                ReminderTest(
                    id = i.toLong(),
                    status = "READY",
                    canceledReason = null,
                    updatedDateTime = null
                )
            )
        }
        repository.saveAll(list)
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun testCase1(){
        val ids: MutableList<Long> = mutableListOf()
        for (i in 1..90000) {
            ids.add(
                i.toLong()
            )
        }

        println("시작@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        val startTime = System.currentTimeMillis()

        repository.updateBulkStatusAndCanceledReason(
            ids,
            "CANCELED",
            "취소"
        )

        val endTime = System.currentTimeMillis()

        println("$startTime ///// $endTime")


        val executionTime = endTime - startTime
        println("실행 시간: $executionTime 밀리초")
        //실행 시간: 20832 밀리초
    }

    @Test
    @Transactional
    fun testCase2(){

        val reminders = mutableListOf<ReminderTest>()
        for (i in 1..90000) {
            val entity = repository.getById(i.toLong())
            entity.canceledReason = "취소"
            entity.status = "CANCELED@"
            reminders.add(entity)
        }

        val startTime = System.currentTimeMillis()
        entityManager.flush()
        val endTime = System.currentTimeMillis()

        val executionTime = endTime - startTime
        println("실행 시간: $executionTime 밀리초")
        //실행 시간: 1229 밀리초

        println(repository.getById(88888))
    }
}