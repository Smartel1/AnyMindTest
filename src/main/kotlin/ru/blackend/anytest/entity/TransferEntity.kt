package ru.blackend.anytest.entity

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "transfers")
data class TransferEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    var datetime: ZonedDateTime,

    var amount: BigDecimal,

    @Column(name = "created_at")
    var createdAt: ZonedDateTime = ZonedDateTime.now()
)