package org.contourgara.garaphotospringboot.application.dto

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.domain.Token
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class FindTokenDtoTest {
  @Test
  fun `ドメインオブジェクトからインスタンスを生成できる`() {
    // setup
    val token = Token("accessToken", "refreshTOken", "clientId", ZonedDateTime.now())

    // execute
    val actual = FindTokenDto.of(token)

    // assert
    val expected = FindTokenDto("accessToken")
    assertThat(actual).isEqualTo(expected)
  }
}
