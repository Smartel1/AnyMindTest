package ru.blackend.anytest.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import ru.blackend.anytest.dto.HistoryRequest
import ru.blackend.anytest.dto.Transfer
import ru.blackend.anytest.entity.TransferEntity
import ru.blackend.anytest.repository.TransferRepository
import java.math.BigDecimal
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime

@ExtendWith(MockitoExtension::class)
class TransferServiceTest {
    @Mock private lateinit var transferRepository: TransferRepository
    @Mock private lateinit var transferMapper: TransferMapper
    @InjectMocks private lateinit var sut: TransferService
    @Captor private lateinit var captor: ArgumentCaptor<TransferEntity>

    @Test
    fun testSave() {
        // given
        val date = ZonedDateTime.of(2023, 3, 2, 1,3,4,0, UTC)
        val amount = BigDecimal("10.2")
        val request = Transfer(date, amount)
        //when
        sut.save(request)
        // then
        verify(transferRepository).save(captor.capture())
        assertThat(captor.value).usingRecursiveComparison()
            .ignoringFields("id", "createdAt")
            .isEqualTo(TransferEntity(datetime = date, amount = amount))
    }

    @Test
    fun testGetHistory() {
        // given
        val from = ZonedDateTime.of(2023, 3, 2, 1,3,4,0, UTC)
        val to = ZonedDateTime.of(2023, 3, 2, 3,23,40,0, UTC)
        val date0 = ZonedDateTime.of(1970, 1, 1, 0,0,0,0, UTC)
        val date1 = ZonedDateTime.of(2023, 3, 2, 2,0,0,0, UTC)
        val date2 = ZonedDateTime.of(2023, 3, 2, 3,0,0,0, UTC)

        val amount1 = BigDecimal.valueOf(0)
        val amount2 = BigDecimal.valueOf(15)
        given(transferRepository.getAmountByInterval(eq(date0), eq(date1), any()))
            .willReturn(amount1)
        given(transferRepository.getAmountByInterval(eq(date1), eq(date2), any()))
            .willReturn(amount2)

        val request = HistoryRequest(from, to)

        val expectedResponse = listOf(
            Transfer(date1, amount1),
            Transfer(date2, amount2)
        )
        //when
        val result = sut.getHistory(request)

        // then
        assertThat(result).containsExactlyElementsOf(expectedResponse)
    }
}