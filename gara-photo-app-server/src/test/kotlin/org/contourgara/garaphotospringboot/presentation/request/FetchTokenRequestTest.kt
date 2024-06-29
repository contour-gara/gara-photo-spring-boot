package org.contourgara.garaphotospringboot.presentation.request

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.param.FetchTokenParam
import org.junit.jupiter.api.Test

class FetchTokenRequestTest {
    @Test
    fun `パラムに変換できる`() {
        // setup
        val sut = FetchTokenRequest("dummy", "dummy")

        // execute
        val actual = sut.convertToParam()

        // assert
        val expected = FetchTokenParam("dummy", "dummy")

        assertThat(actual).isEqualTo(expected)
    }
}
