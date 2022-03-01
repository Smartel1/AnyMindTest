package ru.blackend.anytest.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blackend.anytest.dto.HistoryRequest
import ru.blackend.anytest.dto.Transfer
import ru.blackend.anytest.service.RequestValidator
import ru.blackend.anytest.service.TransferService

@RestController
@RequestMapping("/api/v1/transfers")
class TransfersController(
    private val transferService: TransferService,
    private val requestValidator: RequestValidator
) {
    @PostMapping
    fun save(@RequestBody request: Transfer): Transfer {
        requestValidator.validateSaveRequest(request)
        return transferService.save(request)
    }

    @PostMapping("history")
    fun getHistory(@RequestBody request: HistoryRequest): List<Transfer> {
        requestValidator.validateHistoryRequest(request)
        return transferService.getHistory(request)
    }
}