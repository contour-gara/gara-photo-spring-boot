package org.contourgara.garaphotospringboot.presentation.response

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.dto.CreateUrlDto
import org.junit.jupiter.api.Test

class CreateUrlResponseTest {
  @Test
  fun `DTO からインスタンスを生成できる`() {
    // setup
    val createUrlDto = CreateUrlDto("url", "challenge")

    // execute
    val actual = CreateUrlResponse.of(createUrlDto)

    // assert
    val expected = CreateUrlResponse("url", "challenge")

    assertThat(actual).isEqualTo(expected)
  }
}
