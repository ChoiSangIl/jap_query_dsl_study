package com.example.querydslstudy.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

@Entity
data class ReminderTest (
    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Long = 0,

    @Column(name = "reminderStatus")
    var status: String,

    @Column(name = "cancelReason")
    var canceledReason: String? = null,

    @Column(name = "updateDateTime")
    var updatedDateTime: LocalDateTime? = null
)


interface ReminderRepository : JpaRepository<ReminderTest, Long> {
    @Modifying(clearAutomatically = true)
    @Query(
        """
        UPDATE ReminderTest AS r 
        SET r.status = :nextStatus,
            r.canceledReason = :cancelReason,
            r.updatedDateTime = CURRENT_TIMESTAMP
        WHERE r.id IN (:reminderIds)
    """
    )
    fun updateBulkStatusAndCanceledReason(
        reminderIds: List<Long>,
        nextStatus: String,
        cancelReason: String? = null
    )

}


