package ru.blackend.anytest.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blackend.anytest.dto.HistoryRequest
import ru.blackend.anytest.dto.Transfer
import ru.blackend.anytest.entity.TransferEntity
import ru.blackend.anytest.repository.TransferRepository
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalTime
import java.time.ZonedDateTime

@Service
@Transactional
class TransferService(
    private val transferRepository: TransferRepository,
    private val transferMapper: TransferMapper
) {
    /**
     * Save BTC to a wallet with specified date
     */
    fun save(transfer: Transfer): Transfer {
        val entity = TransferEntity(amount = transfer.amount, datetime = transfer.datetime)
        transferRepository.save(entity)
        return transferMapper.map(entity)
    }

    /**
     * Get history of the wallet balance at the end of each `hour` between two dates (using user time zone)
     */
    fun getHistory(historyRequest: HistoryRequest): List<Transfer> {
        // in order to avoid inconsistency
        // we ignore transfers which is created during history calculation
        // by filtering raws by creation timestamp
        val createdBefore = ZonedDateTime.now()

        val clientTimeZone = historyRequest.startDatetime.zone
        val epochTimePoint = ZonedDateTime.ofInstant(Instant.EPOCH, clientTimeZone)
        val firstTimePoint = with(historyRequest.startDatetime) {
            ZonedDateTime.of(toLocalDate(), LocalTime.of(hour + 1, 0), clientTimeZone)
        }

        val hourIntervals = generateSequence(Pair(epochTimePoint, firstTimePoint)) {
            Pair(it.second, it.second.plusHours(1))
        }.takeWhile { it.second.isBefore(historyRequest.endDatetime) }

        var amount = BigDecimal.ZERO
        return hourIntervals
            .map {
                val amountForPeriod = transferRepository.getAmountByInterval(it.first, it.second, createdBefore)
                amount = amount.plus(amountForPeriod).stripTrailingZeros()
                Transfer(it.second, amount)
            }
            .toList()
    }
}
