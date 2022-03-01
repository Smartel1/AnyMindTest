package ru.blackend.anytest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.blackend.anytest.entity.TransferEntity
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Repository
interface TransferRepository: JpaRepository<TransferEntity, UUID> {
    @Query("select coalesce(sum(t.amount), 0) " +
            "from TransferEntity t " +
            "where t.datetime between :from and :to " +
            "and t.createdAt < :createdBefore")
    fun getAmountByInterval(from: ZonedDateTime, to: ZonedDateTime, createdBefore: ZonedDateTime): BigDecimal
}