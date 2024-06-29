package org.contourgara.garaphotospringboot.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class TokenTest {
    @ParameterizedTest(name = "トークン取得が 21 時で 22 時 {0} 分 {1} 秒に実行した場合、{2} が返る")
    @CsvSource(
        delimiter = '|', value = [
            "55 |  0 | true",
            "54 | 59 | false",
        ]
    )
    fun `トークンが発行から 1 時間 55 分経過している場合、true を返す`(minute: Int, second: Int, expected: Boolean) {
        // setup
        val sut = Token(
            "accessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 21, 0, 0), ZoneId.systemDefault())
        )

        // execute
        val actual =
            sut.isInvalid(ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 22, minute, second), ZoneId.systemDefault()))

        // assert
        assertThat(actual).isEqualTo(expected)
    }
}
