package org.contourgara.garaphotospringboot.presentation.response

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.dto.FindTokenDto
import org.junit.jupiter.api.Test

class FindTokenResponseTest {
    @Test
    fun `DTO からインスタンスを生成できる`() {
        // setup
        val findTokenDto = FindTokenDto("accessToken")

        // execute
        val actual = FindTokenResponse.of(findTokenDto)

        // assert
        val expected = FindTokenResponse("accessToken")
        assertThat(actual).isEqualTo(expected)
    }
}
