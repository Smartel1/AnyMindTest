package ru.blackend.anytest.service

import org.springframework.stereotype.Component
import ru.blackend.anytest.dto.Transfer
import ru.blackend.anytest.entity.TransferEntity

@Component
class TransferMapper {
    fun map(transferEntity: TransferEntity) = Transfer(
            datetime = transferEntity.datetime,
            amount = transferEntity.amount.stripTrailingZeros())
}