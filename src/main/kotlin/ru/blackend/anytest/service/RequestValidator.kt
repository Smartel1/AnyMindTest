package ru.blackend.anytest.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import ru.blackend.anytest.dto.HistoryRequest
import ru.blackend.anytest.dto.Transfer

@Component
class RequestValidator {
    fun validateSaveRequest(request: Transfer) {
        if (request.amount.signum() <= 0) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "my wallet doesn't allow thievery!")
        }
        if (request.amount.precision() > 20) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "amount must have up to 20 numbers")
        }
        if (request.amount.scale() > 10) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "amount must have up to 10 decimal numbers")
        }
    }

    fun validateHistoryRequest(request: HistoryRequest) {
        if (!request.startDatetime.isBefore(request.endDatetime)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "start date must be before end date")
        }
    }
}