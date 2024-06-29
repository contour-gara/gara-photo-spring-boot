package org.contourgara.garaphotospringboot.infrastructure

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.domain.Token
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class TokenEntityTest {
    @Test
    fun `ドメインオブジェクトからインスタンスを生成できる`() {
        // setup
        val token = Token(
            "accessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 5, 17, 20), ZoneId.systemDefault())
        )

        // execute
        val actual = TokenEntity.of(token)

        // assert
        val expected = TokenEntity("accessToken", "refreshToken", "2024-04-14T05:17:20+09:00[Asia/Tokyo]")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `ドメインオブジェクトに変換できる`() {
        // setup
        val sut = TokenEntity("accessToken", "refreshToken", "2024-04-14T05:17:20+09:00[Asia/Tokyo]")

        // execute
        val actual = sut.convertToModel("clientId")

        // assert
        val expected = Token(
            "accessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 5, 17, 20), ZoneId.systemDefault())
        )
        assertThat(actual).isEqualTo(expected)
    }
}
