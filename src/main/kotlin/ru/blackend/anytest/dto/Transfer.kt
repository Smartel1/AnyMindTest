package ru.blackend.anytest.dto

import java.math.BigDecimal
import java.time.ZonedDateTime

data class Transfer(
    val datetime: ZonedDateTime,
    val amount: BigDecimal
)
