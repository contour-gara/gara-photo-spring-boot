package org.contourgara.garaphotospringboot.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDateTime

class TokenTest {
    @ParameterizedTest(name = "トークン取得が 21 時で 22 時 {0} 分 {1} 秒に実行した場合、{2} が返る")
    @CsvSource(
        delimiter = '|',
        value = [
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
            LocalDateTime.of(2024, 4, 14, 21, 0, 0)
        )

        // execute
        val actual = sut.isInvalid(LocalDateTime.of(2024, 4, 14, 22, minute, second))

        // assert
        assertThat(actual).isEqualTo(expected)
    }
}
