package ru.blackend.anytest.dto

import java.time.ZonedDateTime

data class HistoryRequest(
    val startDatetime: ZonedDateTime,
    val endDatetime: ZonedDateTime
)
